package com.studiogoat.pawtrait.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.studiogoat.pawtrait.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhotoTagDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhotoTagDTO.class);
        PhotoTagDTO photoTagDTO1 = new PhotoTagDTO();
        photoTagDTO1.setId("id1");
        PhotoTagDTO photoTagDTO2 = new PhotoTagDTO();
        assertThat(photoTagDTO1).isNotEqualTo(photoTagDTO2);
        photoTagDTO2.setId(photoTagDTO1.getId());
        assertThat(photoTagDTO1).isEqualTo(photoTagDTO2);
        photoTagDTO2.setId("id2");
        assertThat(photoTagDTO1).isNotEqualTo(photoTagDTO2);
        photoTagDTO1.setId(null);
        assertThat(photoTagDTO1).isNotEqualTo(photoTagDTO2);
    }
}
