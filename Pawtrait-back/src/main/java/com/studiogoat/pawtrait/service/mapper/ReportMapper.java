package com.studiogoat.pawtrait.service.mapper;

import com.studiogoat.pawtrait.domain.Photo;
import com.studiogoat.pawtrait.domain.Report;
import com.studiogoat.pawtrait.domain.User;
import com.studiogoat.pawtrait.service.dto.PhotoDTO;
import com.studiogoat.pawtrait.service.dto.ReportDTO;
import com.studiogoat.pawtrait.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Report} and its DTO {@link ReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportMapper extends EntityMapper<ReportDTO, Report> {
    @Mapping(target = "reportedBy", source = "reportedBy", qualifiedByName = "userId")
    @Mapping(target = "resolvedBy", source = "resolvedBy", qualifiedByName = "userId")
    @Mapping(target = "photo", source = "photo", qualifiedByName = "photoId")
    ReportDTO toDto(Report s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("photoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PhotoDTO toDtoPhotoId(Photo photo);
}
