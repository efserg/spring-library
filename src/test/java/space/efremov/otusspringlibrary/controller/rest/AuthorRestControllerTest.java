package space.efremov.otusspringlibrary.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import space.efremov.otusspringlibrary.domain.Author;
import space.efremov.otusspringlibrary.repository.AuthorRepository;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@SpringBootTest
public class AuthorRestControllerTest {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private RestDocumentationResultHandler documentationHandler;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private final String URL = "http://localhost:8080/authors/";

    @Before
    public void setUp() {
        this.documentationHandler = document("{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.documentationHandler)
                .build();
    }

    @Test
    public void deleteAuthorExample() throws Exception {

        authorRepository.deleteAll();

        final Author richardStallman = new Author("Richard Matthew Stallman");
        authorRepository.save(richardStallman);

        final String urlTemplate = URL + "{id}";

        mockMvc.perform(delete(urlTemplate, richardStallman.getId()))
                .andExpect(status().isNoContent())
                .andDo(this.documentationHandler.document(
                        pathParameters(
                                parameterWithName("id").description("The author's ID"))

                ));
    }

    @Test
    public void getAllAuthorsExample() throws Exception {

        authorRepository.deleteAll();

        final Author richardStallman = new Author("Richard Matthew Stallman");
        final Author dennisRitchie = new Author("Dennis MacAlistair Ritchie");
        authorRepository.saveAll(Arrays.asList(richardStallman, dennisRitchie));

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        responseFields(
                                subsectionWithPath("_embedded.authorList").description("An array of <<resources-author,Author resources>>"))));
    }

    @Test
    public void getAuthorExample() throws Exception {

        authorRepository.deleteAll();

        final Author brian = new Author("Brian Wilson Kernighan");
        authorRepository.save(brian);
        final String urlTemplate = URL + "{id}";
        mockMvc.perform(get(urlTemplate, brian.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(brian.getName())))
                .andExpect(jsonPath("_links.self.href", is(MessageFormat.format(URL + "{0}", brian.getId()))))
                .andExpect(jsonPath("_links.author-books", is(notNullValue())))
                .andDo(this.documentationHandler.document(
                        pathParameters(
                                parameterWithName("id").description("The author's ID")),
                        links(
                                linkWithRel("self").description("This <<resources-author,author>>"),
                                linkWithRel("author-books").description("The <<resources-tagged-books,books>> that have this author")),
                        responseFields(
                                fieldWithPath("name").description("The name of the author"),
                                subsectionWithPath("_links").description("<<resources-tag-links,Links>> to other resources"))));
    }

    @Test
    public void createAuthorExample() throws Exception {

        authorRepository.deleteAll();

        final Map<String, String> author = new HashMap<String, String>() {{
            put("name", "Andrew Stuart Tanenbaum");
        }};

        ConstrainedFields fields = new ConstrainedFields(AuthorInput.class);

        this.mockMvc
                .perform(post("/authors")
                        .contentType(MediaTypes.HAL_JSON)
                        .content(this.objectMapper.writeValueAsString(author)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name", is(author.get("name"))))
                .andExpect(jsonPath("_links.self.href", is(notNullValue())))
                .andExpect(jsonPath("_links.author-books", is(notNullValue())))
                .andDo(this.documentationHandler.document(
                        requestFields(
                                fields.withPath("name").description("The author's name")),
                        links(
                                linkWithRel("self").description("This <<resources-author,author>>"),
                                linkWithRel("author-books").description("The <<resources-tagged-books,books>> that have this author")),
                        responseFields(
                                fieldWithPath("name").description("The name of the author"),
                                subsectionWithPath("_links").description("<<resources-tag-links,Links>> to other resources")))
                );
    }

}