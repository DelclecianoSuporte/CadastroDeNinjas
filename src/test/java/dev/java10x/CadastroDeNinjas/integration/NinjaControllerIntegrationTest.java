package dev.java10x.CadastroDeNinjas.integration;

import dev.java10x.CadastroDeNinjas.domain.model.UsuarioModel;
import dev.java10x.CadastroDeNinjas.infra.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NinjaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();

        UsuarioModel admin = new UsuarioModel();
        admin.setUsername("adm");
        admin.setPassword(passwordEncoder.encode("Eteste@10"));
        admin.setRole("ADMIN");

        usuarioRepository.save(admin);

        UsuarioModel user = new UsuarioModel();
        user.setUsername("del");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRole("USER");

        usuarioRepository.save(user);
    }

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

        mockMvc.perform(get("/ninjas/2"))
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

        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String respostaLogin = loginResult.getResponse().getContentAsString();
        String token = JsonPath.read(respostaLogin, "$.token");

        // Cria um ninja com e-mail único para garantir um registro válido
        String emailUnico = "consulta" + System.currentTimeMillis() + "@email.com";

        String ninjaJson = """
        {
            "nome":"Ninja Consulta",
            "email":"%s",
            "idade":20
        }
        """.formatted(emailUnico);

        MvcResult criacaoResult = mockMvc.perform(
                        post("/ninjas")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ninjaJson))
                .andExpect(status().isOk())
                .andReturn();

        String respostaCriacao = criacaoResult.getResponse().getContentAsString();
        Object ninjaId = JsonPath.read(respostaCriacao, "$.id");

        mockMvc.perform(get("/ninjas/" + ninjaId)
                        .header("Authorization", "Bearer " + token))
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

        String respostaLogin = loginResult.getResponse().getContentAsString();
        String token = JsonPath.read(respostaLogin, "$.token");

        // Cria um ninja com e-mail único para evitar conflito com execuções anteriores
        String emailUnico = "naruto" + System.currentTimeMillis() + "@konoha.com";

        String ninjaCriacaoJson = """
        {
            "nome":"Naruto",
            "email":"%s",
            "idade":17
        }
        """.formatted(emailUnico);

        MvcResult criacaoResult = mockMvc.perform(post("/ninjas")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ninjaCriacaoJson)
                )
                .andExpect(status().isOk())
                .andReturn();

        String respostaCriacao = criacaoResult.getResponse().getContentAsString();
        Object ninjaId = JsonPath.read(respostaCriacao, "$.id");

        // Atualiza o ninja recém-criado
        String ninjaAtualizadoJson = """
        {
            "nome":"Naruto Atualizado",
            "email":"%s",
            "idade":18
        }
        """.formatted(emailUnico);

        mockMvc.perform(put("/ninjas/" + ninjaId)
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ninjaAtualizadoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Naruto Atualizado"));
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

        // Cria um ninja com e-mail único para evitar conflito com execuções anteriores
        String emailUnico = "deletar" + System.currentTimeMillis() + "@email.com";

        String ninjaJson = """
        {
            "nome":"Ninja Para Deletar",
            "email":"%s",
            "idade":25
        }
        """.formatted(emailUnico);

        MvcResult criacaoResult = mockMvc.perform(
                        post("/ninjas")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ninjaJson))
                .andExpect(status().isOk())
                .andReturn();

        String respostaCriacao = criacaoResult.getResponse().getContentAsString();

        Object ninjaId = JsonPath.read(respostaCriacao, "$.id");

        mockMvc.perform(delete("/ninjas/" + ninjaId)
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
