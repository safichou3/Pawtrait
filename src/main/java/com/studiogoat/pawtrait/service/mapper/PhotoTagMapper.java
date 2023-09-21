package com.studiogoat.pawtrait.service.mapper;

import com.studiogoat.pawtrait.domain.Photo;
import com.studiogoat.pawtrait.domain.PhotoTag;
import com.studiogoat.pawtrait.domain.Tag;
import com.studiogoat.pawtrait.service.dto.PhotoDTO;
import com.studiogoat.pawtrait.service.dto.PhotoTagDTO;
import com.studiogoat.pawtrait.service.dto.TagDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PhotoTag} and its DTO {@link PhotoTagDTO}.
 */
@Mapper(componentModel = "spring")
public interface PhotoTagMapper extends EntityMapper<PhotoTagDTO, PhotoTag> {
    @Mapping(target = "user", source = "user", qualifiedByName = "tagId")
    @Mapping(target = "photo", source = "photo", qualifiedByName = "photoId")
    PhotoTagDTO toDto(PhotoTag s);

    @Named("tagId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TagDTO toDtoTagId(Tag tag);

    @Named("photoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PhotoDTO toDtoPhotoId(Photo photo);
}
