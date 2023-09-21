package com.studiogoat.pawtrait.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhotoTagMapperTest {

    private PhotoTagMapper photoTagMapper;

    @BeforeEach
    public void setUp() {
        photoTagMapper = new PhotoTagMapperImpl();
    }
}
