package com.albayrakenesfaik.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.albayrakenesfaik.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppoitmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appoitment.class);
        Appoitment appoitment1 = new Appoitment();
        appoitment1.setId(1L);
        Appoitment appoitment2 = new Appoitment();
        appoitment2.setId(appoitment1.getId());
        assertThat(appoitment1).isEqualTo(appoitment2);
        appoitment2.setId(2L);
        assertThat(appoitment1).isNotEqualTo(appoitment2);
        appoitment1.setId(null);
        assertThat(appoitment1).isNotEqualTo(appoitment2);
    }
}
