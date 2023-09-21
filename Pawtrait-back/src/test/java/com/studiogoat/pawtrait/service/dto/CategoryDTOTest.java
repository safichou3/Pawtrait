package com.studiogoat.pawtrait.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.studiogoat.pawtrait.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryDTO.class);
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId("id1");
        CategoryDTO categoryDTO2 = new CategoryDTO();
        assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
        categoryDTO2.setId(categoryDTO1.getId());
        assertThat(categoryDTO1).isEqualTo(categoryDTO2);
        categoryDTO2.setId("id2");
        assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
        categoryDTO1.setId(null);
        assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
    }
}
