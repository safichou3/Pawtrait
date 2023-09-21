package com.studiogoat.pawtrait.service.mapper;

import com.studiogoat.pawtrait.domain.Category;
import com.studiogoat.pawtrait.domain.Photo;
import com.studiogoat.pawtrait.domain.User;
import com.studiogoat.pawtrait.service.dto.CategoryDTO;
import com.studiogoat.pawtrait.service.dto.PhotoDTO;
import com.studiogoat.pawtrait.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Photo} and its DTO {@link PhotoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PhotoMapper extends EntityMapper<PhotoDTO, Photo> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    PhotoDTO toDto(Photo s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);
}
