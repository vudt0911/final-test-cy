package com.example.ontapspring0205.service;

import com.example.ontapspring0205.dto.CategoryDto;
import com.example.ontapspring0205.dto.HomeDto;
import com.example.ontapspring0205.entity.home.HomeEntity;
import com.example.ontapspring0205.model.CategoryModel;
import com.example.ontapspring0205.model.HomeModel;
import com.example.ontapspring0205.repository.ICategoryRepository;
import com.example.ontapspring0205.repository.IHomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {
    @Autowired
    IHomeRepository homeRepository;
    @Autowired
    ICategoryRepository categoryRepository;
    @Autowired
    StorageService storageService;

    public List<HomeDto> getHomes(Pageable pageable) {
        List<HomeDto> homeDtoList = homeRepository.findAll(pageable).stream().map(homeEntity -> HomeDto.entityToDto(homeEntity)).collect(java.util.stream.Collectors.toList());
        return homeDtoList;
    }

    public Page<HomeDto> getHomesPage(Pageable pageable) {
        Page<HomeDto> homeDtoPage = homeRepository.findAll(pageable).map(homeEntity -> HomeDto.entityToDto(homeEntity));
        return homeDtoPage;
    }

    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(categoryEntity -> CategoryDto.entityToDto(categoryEntity)).collect(java.util.stream.Collectors.toList());
    }

    public Boolean saveHome(HomeModel homeModel, MultipartFile file, Long categoryId) {
        String fileName = "";
        if (homeModel != null) {
            homeModel.setSaleQuantity(0);
            if (file != null) {
                fileName = storageService.uploadFile(file);
            }
            homeModel.setImage(fileName);
            homeModel.setStatus(true);
            homeModel.setCategoryModel(CategoryModel.entityToModel(categoryRepository.findById(categoryId).get()));
            HomeEntity newHomeEntity = homeModel.modelToEntity(homeModel);
            homeRepository.save(newHomeEntity);
            return true;
        } else {
            return false;
        }
    }

    public Boolean updateHome(HomeModel homeModel, MultipartFile file, Long categoryId) {
        String fileName = "";
        HomeEntity homeExist = homeRepository.findById(homeModel.getId()).get();
        if (homeExist != null) {
            if (file != null) {
                fileName = storageService.uploadFile(file);
            } else if (file == null && homeRepository.findById(homeModel.getId()).isPresent()) {
                fileName = homeRepository.findById(homeModel.getId()).get().getImage();
            }
            homeExist.setSaleQuantity(0);
            homeExist.setImage(fileName);
            homeExist.setName(homeModel.getName());
            homeExist.setPrice(homeModel.getPrice());
            homeExist.setCategory(CategoryModel.modelToEntity(homeModel.getCategoryModel()));
            homeExist.setStatus(true);
            homeExist.setDescription(homeModel.getDescription());
            homeExist.setQuantity(homeModel.getQuantity());
            homeRepository.save(homeExist);
            return true;
        }
        return false;
    }

    public HomeModel getHomeById(Long id) {
        return HomeModel.entityToModel(homeRepository.findById(id).get());
    }

    public Boolean deleteHome(Long id) {
        if (homeRepository.findById(id).isPresent()) {
            homeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Page<HomeDto> getHomeByName(Pageable pageable, String name) {
        Page<HomeDto> filterHomeDtoPageByName = homeRepository.findByNameLike("%".concat(name).concat("%"), pageable).map(homeEntity -> HomeDto.entityToDto(homeEntity));
        return filterHomeDtoPageByName;
    }

    public Page<HomeDto> getHomeByCategoryPage(Pageable pageable, Long categoryId) {
        Page<HomeDto> filterHomeDtoPageBycategoryId = homeRepository.findByCategoryId(categoryId, pageable).map(homeEntity -> HomeDto.entityToDto(homeEntity));
        return filterHomeDtoPageBycategoryId;
    }

    public List<HomeDto> getHomeByCategoryId(Long categoryId) {
        return homeRepository.findByCategoryId(categoryId).stream().map(homeEntity -> HomeDto.entityToDto(homeEntity)).collect(Collectors.toList());
    }

    public String buiderChart(Long categoryId) {
        List<HomeDto> homeDtos = getHomeByCategoryId(categoryId);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"[[\\\"Task\\\", \\\"Hours per Day\\\"],");
        for (HomeDto homeDto : homeDtos) {
            stringBuilder.append("[\\\"").append(homeDto.getName()).append("\\\",").append(homeDto.getQuantityDto()).append("],");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]\"");
        return stringBuilder.toString();
    }

}
