package com.tallerwebi.integracion;

import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.dominio.model.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorLoginTest {

	private Usuario usuarioMock; // Este mock no parece usarse en los tests actuales, puedes eliminarlo si no lo necesitas.

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;


	@BeforeEach
	public void init(){
		// usuarioMock = mock(Usuario.class); // Eliminar si no se usa
		// when(usuarioMock.getEmail()).thenReturn("dami@unlam.com"); // Eliminar si no se usa
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void debeRetornarLaPaginaLoginCuandoSeNavegaALaRaiz() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/"))
		/*.andDo(print())*/
		.andExpect(status().is3xxRedirection()) // Espera una redirección
		.andReturn();

		ModelAndView modelAndView = result.getModelAndView();
		assert modelAndView != null;
		// ¡AHORA ESPERAMOS QUE REDIRIJA A /login!
		assertThat("redirect:/login", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
		assertThat(true, is(modelAndView.getModel().isEmpty())); // La redirección no debería añadir nada al modelo
	}

	@Test
	public void debeRetornarLaPaginaLoginCuandoSeNavegaALLogin() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/login"))
		.andExpect(status().isOk()) // Espera un OK 200
		.andReturn();

		ModelAndView modelAndView = result.getModelAndView();
		assert modelAndView != null;
		// ¡AHORA ESPERAMOS LA VISTA "login"!
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		// Verificar que el modelo contiene "datosLogin"
		assertThat(modelAndView.getModel().get("datosLogin").toString(),  containsString("com.tallerwebi.presentacion.DatosLogin"));
	}
}