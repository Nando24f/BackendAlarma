package testing.class_project;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for usuarios and alarmas database operations.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class TestingController {

    private final JdbcTemplate jdbcTemplate;
    private final AccessControlService accessControlService;
    private final QueryRepository queryRepository;

    public TestingController(JdbcTemplate jdbcTemplate,
            AccessControlService accessControlService,
            QueryRepository queryRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.accessControlService = accessControlService;
        this.queryRepository = queryRepository;
    }

    // 1. Lista de vecinos registrados
    @GetMapping("/vecinos")
    public ResponseEntity<List<Map<String, Object>>> getVecinos() {
        if (!accessControlService.canExecuteQuery1()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query1"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 2. Lista de Administradores
    @GetMapping("/administradores")
    public ResponseEntity<List<Map<String, Object>>> getAdministradores() {
        if (!accessControlService.canExecuteQuery2()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query2"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 3. Lista de eventos (alarmas)
    @GetMapping("/alarmas")
    public ResponseEntity<List<Map<String, Object>>> getAlarmas() {
        if (!accessControlService.canExecuteQuery3()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query3"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 4. Porcentaje de hombres que generaron alarmas
    @GetMapping("/alarmas/hombres/porcentaje")
    public ResponseEntity<List<Map<String, Object>>> getPorcentajeHombresAlarmas() {
        if (!accessControlService.canExecuteQuery4()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query4"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 5. Porcentaje de mujeres que generaron alarmas
    @GetMapping("/alarmas/mujeres/porcentaje")
    public ResponseEntity<List<Map<String, Object>>> getPorcentajeMujeresAlarmas() {
        if (!accessControlService.canExecuteQuery5()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query5"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 6. Cantidad de Hombres registrados
    @GetMapping("/usuarios/hombres/cantidad")
    public ResponseEntity<List<Map<String, Object>>> getCantidadHombres() {
        if (!accessControlService.canExecuteQuery6()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query6"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 7. Cantidad de Mujeres registradas
    @GetMapping("/usuarios/mujeres/cantidad")
    public ResponseEntity<List<Map<String, Object>>> getCantidadMujeres() {
        if (!accessControlService.canExecuteQuery7()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query7"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/calles")
    public ResponseEntity<List<String>> getCalles() {
        return ResponseEntity.ok(List.of("Calle 1", "Calle 2", "Calle 3"));
    }

    // 8. Cantidad de vecinos de X calle (modificado para usar LIKE con %)
    @GetMapping("/vecinos/calle")
    public ResponseEntity<List<Map<String, Object>>> getVecinosPorCalle(@RequestParam String calle) {
        if (!accessControlService.canExecuteQuery8()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            var results = jdbcTemplate.queryForList(
                    queryRepository.getQuery("query8"),
                    "%" + calle + "%"
            );
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 9. Cantidad de mujeres de X calle (modificado para usar LIKE con %)
    @GetMapping("/usuarios/mujeres/calle")
    public ResponseEntity<List<Map<String, Object>>> getMujeresPorCalle(@RequestParam String calle) {
        if (!accessControlService.canExecuteQuery9()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            var results = jdbcTemplate.queryForList(
                    queryRepository.getQuery("query9"),
                    "%" + calle + "%"
            );
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 10. Cantidad de hombres de X calle (modificado para usar LIKE con %)
    @GetMapping("/usuarios/hombres/calle")
    public ResponseEntity<List<Map<String, Object>>> getHombresPorCalle(@RequestParam String calle) {
        if (!accessControlService.canExecuteQuery10()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            var results = jdbcTemplate.queryForList(
                    queryRepository.getQuery("query10"),
                    "%" + calle + "%"
            );
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
