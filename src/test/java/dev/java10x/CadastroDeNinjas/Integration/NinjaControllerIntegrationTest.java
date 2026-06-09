package dev.java10x.CadastroDeNinjas.Integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NinjaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRealizarLoginComSucesso() throws Exception {

        String json = """
        {
            "username":"adm",
            "password":"Eteste@10"
        }
        """;

        mockMvc.perform(
                        post("/auth/login")
                                .contentType("application/json")
                                .content(json)
                )
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornar403QuandoNaoAutenticado() throws Exception {

        mockMvc.perform(get("/ninjas/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deveRetornarNinjaQuandoAutenticado() throws Exception {

        String loginJson = """
        {
            "username":"adm",
            "password":"Eteste@10"
        }
        """;

        MvcResult loginResult = mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginJson)
                )
                .andExpect(status().isOk())
                .andReturn();

        String respostaLogin =
                loginResult.getResponse().getContentAsString();

        String token =
                JsonPath.read(respostaLogin, "$.token");

        mockMvc.perform(
                        get("/ninjas/1")
                                .header("Authorization",
                                        "Bearer " + token)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void deveCriarNinjaQuandoUsuarioForAdmin() throws Exception {

        // LOGIN
        String loginJson = """
        {
            "username":"adm",
            "password":"Eteste@10"
        }
        """;

        MvcResult loginResult = mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginJson)
                )
                .andExpect(status().isOk())
                .andReturn();

        String respostaLogin =
                loginResult.getResponse().getContentAsString();

        String token =
                JsonPath.read(respostaLogin, "$.token");

        // NINJA
        String ninjaJson = """
        {
            "nome":"Teste Integracao",
            "email":"teste@integracao.com",
            "idade":20
        }
        """;

        mockMvc.perform(
                        post("/ninjas")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ninjaJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome")
                        .value("Teste Integracao"))
                .andExpect(jsonPath("$.email")
                        .value("teste@integracao.com"))
                .andExpect(jsonPath("$.idade")
                        .value(20));
    }

    @Test
    public void deveAtualizarNinjaQuandoUsuarioForAdmin() throws Exception {

    String loginJson = """
    {
        "username":"adm",
        "password":"Eteste@10"
    }
    """;

        MvcResult loginResult = mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginJson)
                )
                .andExpect(status().isOk())
                .andReturn();

        String respostaLogin =
                loginResult.getResponse().getContentAsString();

        String token =
                JsonPath.read(respostaLogin, "$.token");

        String ninjaJson = """
        {
            "nome":"Naruto Atualizado",
            "email":"naruto@konoha.com",
            "idade":18
        }
        """;

        mockMvc.perform(
                        put("/ninjas/1")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ninjaJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome")
                        .value("Naruto Atualizado"));
    }

    @Test
    public void deveDeletarNinjaQuandoUsuarioForAdmin() throws Exception {

    String loginJson = """
    {
        "username":"adm",
        "password":"Eteste@10"
    }
    """;

        MvcResult loginResult = mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String respostaLogin = loginResult.getResponse().getContentAsString();

        String token = JsonPath.read(respostaLogin, "$.token");

        mockMvc.perform(delete("/ninjas/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void deveRetornar403QuandoUsuarioNaoForAdmin() throws Exception {

    String loginJson = """
    {
        "username":"del",
        "password":"1234"
    }
    """;

        MvcResult loginResult = mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginJson)
                )
                .andExpect(status().isOk())
                .andReturn();

        String respostaLogin =
                loginResult.getResponse().getContentAsString();

        String token =
                JsonPath.read(respostaLogin, "$.token");

        // TENTATIVA DE CRIAR NINJA
        String ninjaJson = """
    {
        "nome":"Ninja",
        "email":"ninja@email.com",
        "idade":20
    }
    """;

        mockMvc.perform(
                        post("/ninjas")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ninjaJson))
                .andExpect(status().isForbidden());
    }
}
