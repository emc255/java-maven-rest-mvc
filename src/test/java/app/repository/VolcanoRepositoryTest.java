package app.repository;

import app.bootstrap.BootstrapData;
import app.entity.Volcano;
import app.service.VolcanoCSVImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import({BootstrapData.class, VolcanoCSVImpl.class})
class VolcanoRepositoryTest {
    @Autowired
    VolcanoRepository volcanoRepository;


    @Test
    void findAllByCountryIsLikeIgnoreCase() {
        List<Volcano> volcanoList = volcanoRepository.findAllByCountryIsLikeIgnoreCase("%States%");
        assertThat(volcanoList.size()).isEqualTo(184);
    }

    @Test
    void findAllByRegionIsLikeIgnoreCase() {
        List<Volcano> volcanoList = volcanoRepository.findAllByRegionIsLikeIgnoreCase("%Cape%");
        assertThat(volcanoList.size()).isEqualTo(3);
    }
}