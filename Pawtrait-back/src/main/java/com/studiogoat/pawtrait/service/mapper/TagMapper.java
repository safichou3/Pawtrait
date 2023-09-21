package com.studiogoat.pawtrait.service.mapper;

import com.studiogoat.pawtrait.domain.Tag;
import com.studiogoat.pawtrait.service.dto.TagDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {}
