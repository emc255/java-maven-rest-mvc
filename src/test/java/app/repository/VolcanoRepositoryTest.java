package app.repository;

import app.bootstrap.BootstrapData;
import app.entity.Volcano;
import app.service.VolcanoCSVImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BootstrapData.class, VolcanoCSVImpl.class})
class VolcanoRepositoryTest {
    @Autowired
    VolcanoRepository volcanoRepository;


    @Test
    void findAllByCountryIsLikeIgnoreCase() {
        Page<Volcano> volcanoList = volcanoRepository.findAllByCountryIsLikeIgnoreCase("%States%", null);
        assertThat(volcanoList.getContent().size()).isEqualTo(184);
    }

    @Test
    void findAllByRegionIsLikeIgnoreCase() {
        Page<Volcano> volcanoList = volcanoRepository.findAllByRegionIsLikeIgnoreCase("%Cape%", null);
        assertThat(volcanoList.getContent().size()).isEqualTo(3);
    }
}