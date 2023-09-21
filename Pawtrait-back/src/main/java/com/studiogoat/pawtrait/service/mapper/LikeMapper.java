package com.studiogoat.pawtrait.service.mapper;

import com.studiogoat.pawtrait.domain.Like;
import com.studiogoat.pawtrait.domain.Photo;
import com.studiogoat.pawtrait.domain.User;
import com.studiogoat.pawtrait.service.dto.LikeDTO;
import com.studiogoat.pawtrait.service.dto.PhotoDTO;
import com.studiogoat.pawtrait.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Like} and its DTO {@link LikeDTO}.
 */
@Mapper(componentModel = "spring")
public interface LikeMapper extends EntityMapper<LikeDTO, Like> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "photo", source = "photo", qualifiedByName = "photoId")
    LikeDTO toDto(Like s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("photoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PhotoDTO toDtoPhotoId(Photo photo);
}
