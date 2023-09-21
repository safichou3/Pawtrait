package com.studiogoat.pawtrait.service.mapper;

import com.studiogoat.pawtrait.domain.Category;
import com.studiogoat.pawtrait.service.dto.CategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {}
