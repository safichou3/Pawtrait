package com.studiogoat.pawtrait.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.studiogoat.pawtrait.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhotoTagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhotoTag.class);
        PhotoTag photoTag1 = new PhotoTag();
        photoTag1.setId("id1");
        PhotoTag photoTag2 = new PhotoTag();
        photoTag2.setId(photoTag1.getId());
        assertThat(photoTag1).isEqualTo(photoTag2);
        photoTag2.setId("id2");
        assertThat(photoTag1).isNotEqualTo(photoTag2);
        photoTag1.setId(null);
        assertThat(photoTag1).isNotEqualTo(photoTag2);
    }
}
