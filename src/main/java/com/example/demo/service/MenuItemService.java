package com.example.demo.service;

import com.example.demo.dto.menuitem.CUMenuItemReq;
import com.example.demo.dto.menuitem.MenuItemSortView;
import com.example.demo.dto.price.PriceMenuItemView;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.auth.Customer;
import com.example.demo.model.menu.MenuItem;
import com.example.demo.model.menu.MenuItemCategory;
import com.example.demo.model.menu.MenuItemGroup;
import com.example.demo.model.menu.PriceMenuItem;
import com.example.demo.repository.IMenuItemCategoryRepository;
import com.example.demo.repository.IMenuItemRepository;
import com.example.demo.repository.IPriceMenuItemRepository;
import com.example.demo.repository.ITableHistoryMenuItemRepository;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final IMenuItemRepository menuItemRepository;
    private final IMenuItemCategoryRepository menuItemCategoryRepository;
    private final ModelMapper modelMapper;
    private final ITableHistoryMenuItemRepository tableHistoryMenuItemRepository;
    private final IPriceMenuItemRepository priceMenuItemRepository;
    public String getImageUrl(String name) {
        return "https://storage.googleapis.com/buffet-b2b27.appspot.com/".concat(name);
    }

    public String save(MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Tạo một số ngẫu nhiên dưới dạng UUID và ghép vào tên tệp
        String name = UUID.randomUUID().toString() + extension;

        bucket.create(name, file.getBytes(), file.getContentType());

        return name;
    }

    public String save(BufferedImage bufferedImage, String originalFileName) throws IOException {
        byte[] bytes = getByteArrays(bufferedImage, getExtension(originalFileName));

        Bucket bucket = StorageClient.getInstance().bucket();

        String name = generateFileName(originalFileName);

        bucket.create(name, bytes);

        return name;
    }

    public String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    public String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + getExtension(originalFileName);
    }

    public byte[] getByteArrays(BufferedImage bufferedImage, String format) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            ImageIO.write(bufferedImage, format, baos);

            baos.flush();

            return baos.toByteArray();

        } catch (IOException e) {
            throw e;
        } finally {
            baos.close();
        }
    }

    public ResponseEntity<?> createMenuItem(MultipartFile image,CUMenuItemReq cuMenuItemReq) {
        try {
            String fileName = save(image);
            String urlImage = getImageUrl(fileName);
            MenuItemCategory menuItemCategory = menuItemCategoryRepository.findById(cuMenuItemReq.getMenuItemCategoryId()).get();
            MenuItem menuItem = new MenuItem(cuMenuItemReq.getMenuItemName(),menuItemCategory, MenuItemGroup.valueOf(cuMenuItemReq.getMenuItemGroup()),
                    cuMenuItemReq.getDescription(),urlImage,false);
            menuItem = menuItemRepository.save(menuItem);
            return ResponseEntity.ok(modelMapper.map(menuItem, MenuItemSortView.class));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Upload image or create menu item is failed!");
        }
    }

    public ResponseEntity<?> updateMenuItem(MultipartFile image, CUMenuItemReq cuMenuItemReq) {
        try {
            String fileName = save(image);
            String urlImage = getImageUrl(fileName);
            MenuItem menuItem = menuItemRepository.findById(cuMenuItemReq.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found menu item"));
            MenuItemCategory menuItemCategory = menuItemCategoryRepository.findById(cuMenuItemReq.getMenuItemCategoryId()).get();
            menuItem.setMenuItemName(cuMenuItemReq.getMenuItemName());
            menuItem.setMenuItemGroup(MenuItemGroup.valueOf(cuMenuItemReq.getMenuItemGroup()));
            menuItem.setMenuItemCategory(menuItemCategory);
            menuItem.setDescription(cuMenuItemReq.getDescription());
            menuItem.setActive(cuMenuItemReq.isActive());
            return ResponseEntity.ok("update is successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Upload image or update menu item is failed!");
        }
    }

    @Transactional
    public ResponseEntity<?> deleteMenuItem(Integer menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found menu item"));
        if (tableHistoryMenuItemRepository.isHasTableOrder(menuItem.getMenuItemId())){
            menuItem.setActive(false);
            try {
                menuItemRepository.save(menuItem);
                return ResponseEntity.ok("disable menu item is successfully!");
            }catch (Exception e){
                return ResponseEntity.badRequest().body("Error");
            }
        }
        try {
            menuItemRepository.delete(menuItem);
            return ResponseEntity.ok("delete menu item is successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    public ResponseEntity<?> getAllNow() {
        List<PriceMenuItemView> priceMenuItems = priceMenuItemRepository.findCurrentPricesByMenuItemOnDate(LocalDate.now())
                    .stream().map(priceMenuItem -> modelMapper.map(priceMenuItem,PriceMenuItemView.class))
                    .collect(Collectors.toList());
        return ResponseEntity.ok(priceMenuItems);
    }

    public ResponseEntity<?> getMenuDetail(Integer id) {
        MenuItem menuItem = menuItemRepository.findById(id).get();
        LocalDate cur = LocalDate.now();
        PriceMenuItemView priceMenuItemView = new PriceMenuItemView();
        if(!menuItem.getPriceMenuItemList().isEmpty()){
            PriceMenuItem priceMenuItem = priceMenuItemRepository.findCurPriceByMenuItemOnDate(cur,menuItem.getMenuItemId());
            if(priceMenuItem != null){
                priceMenuItemView = modelMapper.map(priceMenuItem, PriceMenuItemView.class);
            }
        }
        priceMenuItemView.setMenuItem(modelMapper.map(menuItem,MenuItemSortView.class));
        return ResponseEntity.ok(priceMenuItemView);
    }

    public ResponseEntity<?> getAllNow1() {
        List<PriceMenuItemView> priceMenuItems = new ArrayList<>();
        List<MenuItem> menuItems = menuItemRepository.findAll();
        LocalDate cur = LocalDate.now();
        for (MenuItem menuItem : menuItems){
            PriceMenuItemView priceMenuItemView = new PriceMenuItemView();
            if(!menuItem.getPriceMenuItemList().isEmpty()){
                PriceMenuItem priceMenuItem = priceMenuItemRepository.findCurPriceByMenuItemOnDate(cur,menuItem.getMenuItemId());
                if(priceMenuItem != null){
                    priceMenuItemView = modelMapper.map(priceMenuItem, PriceMenuItemView.class);
                }
            }
            priceMenuItemView.setMenuItem(modelMapper.map(menuItem,MenuItemSortView.class));
            priceMenuItems.add(priceMenuItemView);
        }
        return ResponseEntity.ok(priceMenuItems);
    }
}
