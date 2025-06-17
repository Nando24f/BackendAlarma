package testing.class_project;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

/**
 * REST Controller for alarmas vecinales operations.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class TestingController {

    private final JdbcTemplate jdbcTemplate;
    private final QueryRepository queryRepository;

    public TestingController(JdbcTemplate jdbcTemplate,
                              QueryRepository queryRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryRepository = queryRepository;
    }

    private AccessControlService accessControlService;

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint(HttpServletRequest request) {
        // ✅ Obtener IP real desde encabezado si viene de proxy
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }

        if (!accessControlService.isAllowed(ip)) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok("Acceso permitido desde IP: " + ip);
    }

    // 1. Últimas 10 alarmas activas
    @GetMapping("/alarmas/activas")
    public ResponseEntity<List<Map<String, Object>>> getUltimasAlarmasActivas() {
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query1"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 2. Alarmas con ubicación geográfica
    @GetMapping("/alarmas/mapa")
    public ResponseEntity<List<Map<String, Object>>> getAlarmasConUbicacion() {
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query2"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 3. Alarmas por usuario específico
    @GetMapping("/alarmas/usuario/{id}")
    public ResponseEntity<List<Map<String, Object>>> getAlarmasPorUsuario(@PathVariable int id) {
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query3"), id);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 4. Alarmas por rango de fechas
    @GetMapping("/alarmas/rango")
    public ResponseEntity<List<Map<String, Object>>> getAlarmasPorRango(@RequestParam String desde, @RequestParam String hasta) {
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query4"), desde, hasta);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 5. Total de alarmas por categoría
    @GetMapping("/alarmas/categorias")
    public ResponseEntity<List<Map<String, Object>>> getConteoPorCategoria() {
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query5"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 6. Total de alarmas por estado
    @GetMapping("/alarmas/estados")
    public ResponseEntity<List<Map<String, Object>>> getConteoPorEstado() {
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query6"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 7. Total de alarmas por usuario
    @GetMapping("/alarmas/por_usuario")
    public ResponseEntity<List<Map<String, Object>>> getTotalPorUsuario() {
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query7"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 8. Obtener alarma por ID
    @GetMapping("/alarmas/{id}")
    public ResponseEntity<List<Map<String, Object>>> getAlarmaPorId(@PathVariable int id) {
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query8"), id);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 9. Alarmas críticas no resueltas
    @GetMapping("/alarmas/criticas")
    public ResponseEntity<List<Map<String, Object>>> getCriticasNoResueltas() {
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query9"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 10. Alarmas resueltas en los últimos 7 días
    @GetMapping("/alarmas/resueltas")
    public ResponseEntity<List<Map<String, Object>>> getResueltasUltimos7Dias() {
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query10"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
