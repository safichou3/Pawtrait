package com.studiogoat.pawtrait.service.mapper;

import com.studiogoat.pawtrait.domain.ApplicationUser;
import com.studiogoat.pawtrait.domain.User;
import com.studiogoat.pawtrait.service.dto.ApplicationUserDTO;
import com.studiogoat.pawtrait.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationUser} and its DTO {@link ApplicationUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApplicationUserMapper extends EntityMapper<ApplicationUserDTO, ApplicationUser> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userId")
    ApplicationUserDTO toDto(ApplicationUser s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
