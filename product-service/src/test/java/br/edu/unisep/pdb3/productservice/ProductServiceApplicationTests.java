package br.edu.unisep.pdb3.productservice;

import br.edu.unisep.pdb3.productservice.repository.ProductRepository;
import br.edu.unisep.pdb3.productservice.dto.ProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer =
			new MongoDBContainer(
					"mongo:4.4.2");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(
			DynamicPropertyRegistry
					dynamicPropertyRegistry){
		dynamicPropertyRegistry.add(
				"spring.data.mongodb.uri",
				mongoDBContainer::getReplicaSetUrl);
	}

	private ProductRequest getProductRequest(){
		return ProductRequest.builder()
				.name("Iphone 17")
				.description("O revolucionario USB A")
				.price(BigDecimal.valueOf(150000))
				.build();
	}

	@Test
	void shouldCreateProduct() throws Exception{
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper
				.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders
						.post("/api/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestString)
						)
				.andExpect(MockMvcResultMatchers
						.status().isCreated());
		Assertions.assertEquals(1,
				productRepository.findAll().size());
	}
}
