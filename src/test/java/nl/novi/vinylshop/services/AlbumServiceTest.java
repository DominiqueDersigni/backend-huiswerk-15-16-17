package nl.novi.vinylshop.services;

import nl.novi.vinylshop.dtos.album.AlbumExtendedResponseDTO;
import nl.novi.vinylshop.dtos.album.AlbumResponseDTO;
import nl.novi.vinylshop.entities.AlbumEntity;
import nl.novi.vinylshop.exceptions.RecordNotFoundException;
import nl.novi.vinylshop.mappers.AlbumDTOMapper;
import nl.novi.vinylshop.mappers.AlbumExtendedDTOMapper;
import nl.novi.vinylshop.repositories.AlbumRepository;
import nl.novi.vinylshop.repositories.ArtistRepository;
import nl.novi.vinylshop.repositories.GenreRepository;
import nl.novi.vinylshop.repositories.PublisherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    @Mock
    AlbumRepository albumRepository;

    @Mock
    ArtistRepository artistRepository;

    @Mock
    AlbumDTOMapper albumDTOMapper;

    @Mock
    AlbumExtendedDTOMapper albumExtendedDTOMapper;

    @Mock
    PublisherRepository publisherRepository;

    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    AlbumService albumService;

    AlbumEntity album1;
    AlbumEntity album2;


    @BeforeEach
    void setUp() {
        album1 = new AlbumEntity();
        album1.setId(1L);
        album1.setTitle("Thriller");
        album1.setReleaseYear(1982);


        album2 = new AlbumEntity();
        album2.setId(2L);
        album2.setTitle("Red");
        album2.setReleaseYear(2012);
    }

    @AfterEach
    void tearDown() {
    }



    @Test
    @DisplayName("findAllAlbums")
    void findAllAlbums() {
        // Arrange
        List<AlbumEntity> entities = List.of(album1, album2);

        AlbumResponseDTO dto1 = new AlbumResponseDTO();
        dto1.setTitle("Thriller");
        AlbumResponseDTO dto2 = new AlbumResponseDTO();
        dto2.setTitle("Red");

        when(albumRepository.findAll()).thenReturn(entities);
        when(albumDTOMapper.mapToDto(entities)).thenReturn(List.of(dto1, dto2));

        // Act
        List<AlbumResponseDTO> result = albumService.findAllAlbums();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Thriller", result.get(0).getTitle());
        assertEquals("Red",result.get(1).getTitle());
    }


    @Test
    @DisplayName("findAlbumById")
    void findAlbumById() {
        // Arrange
        AlbumExtendedResponseDTO dto = new AlbumExtendedResponseDTO();
        dto.setTitle("Thriller");

        when(albumRepository.findById(1L)).thenReturn(Optional.of(album1));
        when(albumExtendedDTOMapper.mapToDto(album1)).thenReturn(dto);

        // Act
        AlbumExtendedResponseDTO result = albumService.findAlbumById(1L);

        // Assert
        assertEquals("Thriller", result.getTitle());
    }

    @Test
    @DisplayName("findAlbumById_throwsException_whenAlbumDoesNotExist")
    void findAlbumById_throwsException_whenAlbumDoesNotExist() {

        // Arrange
        when(albumRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(RecordNotFoundException.class, () -> albumService.findAlbumById(99L));
    }
}