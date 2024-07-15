package br.com.erudio.unittest.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectisNullException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import br.com.erudio.services.PersonServices;
import br.com.erudio.unittest.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

	MockPerson input;

	@InjectMocks
	private PersonServices services;

	@Mock
	PersonRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
		List<Person> list = input.mockEntityList();

		when(repository.findAll()).thenReturn(list);

		var peoples = services.findAll();

		assertNotNull(peoples);
		assertEquals(14, peoples.size());

		var PersonOne = peoples.get(1);

		assertNotNull(PersonOne);
		assertNotNull(PersonOne.getKey());
		assertNotNull(PersonOne.getLinks());
		assertTrue(PersonOne.toString().contains("</api/person/v1/1>;rel=\"self\""));
		assertEquals("Addres Test1", PersonOne.getAddress());
		assertEquals("First Name Test1", PersonOne.getFirstName());
		assertEquals("Last Name Test1", PersonOne.getLastName());
		assertEquals("Female", PersonOne.getGender());

		var PersonFour = peoples.get(4);

		assertNotNull(PersonFour);
		assertNotNull(PersonFour.getKey());
		assertNotNull(PersonFour.getLinks());
		assertTrue(PersonFour.toString().contains("</api/person/v1/4>;rel=\"self\""));
		assertEquals("Addres Test4", PersonFour.getAddress());
		assertEquals("First Name Test4", PersonFour.getFirstName());
		assertEquals("Last Name Test4", PersonFour.getLastName());
		assertEquals("Male", PersonFour.getGender());

		var PersonSeven = peoples.get(7);

		assertNotNull(PersonSeven);
		assertNotNull(PersonSeven.getKey());
		assertNotNull(PersonSeven.getLinks());
		assertTrue(PersonSeven.toString().contains("</api/person/v1/7>;rel=\"self\""));
		assertEquals("Addres Test7", PersonSeven.getAddress());
		assertEquals("First Name Test7", PersonSeven.getFirstName());
		assertEquals("Last Name Test7", PersonSeven.getLastName());
		assertEquals("Female", PersonSeven.getGender());
	}

	@Test
	void testFindById() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		var result = services.findById(1L);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		System.out.println(result.toString());
		assertTrue(result.toString().contains("</api/person/v1/1>;rel=\"self\""));
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testCreate() {
		Person entity = input.mockEntity(1);

		Person persisted = entity;
		persisted.setId(1L);

		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);

		when(repository.save(entity)).thenReturn(persisted);

		var result = services.create(vo);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("</api/person/v1/1>;rel=\"self\""));
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testUpdate() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);

		Person persisted = entity;
		persisted.setId(1L);

		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);

		var result = services.update(vo);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("</api/person/v1/1>;rel=\"self\""));
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testDelete() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		services.delete(1L);
	}

	@Test
	void testCreateWithNullPerson() {

		Exception exception = assertThrows(RequiredObjectisNullException.class, ()->{
			services.create(null);
		});
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	void testUpdateWithNullPerson() {

		Exception exception = assertThrows(RequiredObjectisNullException.class, ()->{
			services.create(null);
		});
		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

}
