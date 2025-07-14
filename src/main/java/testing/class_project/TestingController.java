package testing.class_project;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class TestingController {

    private final JdbcTemplate jdbcTemplate;
    private final QueryRepository queryRepository;
    private final AccessControlService accessControlService;

    @Autowired
    public TestingController(JdbcTemplate jdbcTemplate,
            QueryRepository queryRepository,
            AccessControlService accessControlService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryRepository = queryRepository;
        this.accessControlService = accessControlService;
    }

    private String extraerIPCliente(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        System.out.println("IP que llega: " + ip);
        return ip;
    }

    private boolean accesoDenegado(HttpServletRequest request) {
        String ip = extraerIPCliente(request);
        return !accessControlService.isAllowed(ip);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint(HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok("Acceso permitido al endpoint /test");
    }

    @GetMapping("/alarmas/activas")
    public ResponseEntity<List<Map<String, Object>>> obtenerAlarmas(HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query1"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alarmas/mapa")
    public ResponseEntity<List<Map<String, Object>>> getAlarmasConUbicacion(HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query2"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();  // <--- TEMPORAL para debug
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/alarmas/usuario/{id}")
    public ResponseEntity<List<Map<String, Object>>> getAlarmasPorUsuario(@PathVariable int id, HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query3"), id);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alarmas/rango")
    public ResponseEntity<List<Map<String, Object>>> getAlarmasPorRango(@RequestParam String desde, @RequestParam String hasta, HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query4"), desde, hasta);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alarmas/categorias")
    public ResponseEntity<List<Map<String, Object>>> getConteoPorCategoria(HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query5"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alarmas/estados")
    public ResponseEntity<List<Map<String, Object>>> getConteoPorEstado(HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query6"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alarmas/por_usuario")
    public ResponseEntity<List<Map<String, Object>>> getTotalPorUsuario(HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query7"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alarmas/id/{id}")
    public ResponseEntity<List<Map<String, Object>>> getAlarmaPorId(@PathVariable int id, HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query8"), id);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alarmas/criticas")
    public ResponseEntity<List<Map<String, Object>>> getCriticasNoResueltas(HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query9"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alarmas/resueltas")
    public ResponseEntity<List<Map<String, Object>>> getResueltasUltimos7Dias(HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query10"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alarmas/categorias_distintas")
    public ResponseEntity<List<Map<String, Object>>> getCategoriasAlarmas(HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query11"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alarmas/usuarios")
    public ResponseEntity<List<Map<String, Object>>> getUsuariosConAlarmas(HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query12"));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alarmas/por_categoria")
    public ResponseEntity<List<Map<String, Object>>> getAlarmasPorCategoria(@RequestParam String categoria, HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query13"), categoria);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alarmas/buscar")
    public ResponseEntity<List<Map<String, Object>>> buscarPorTexto(@RequestParam("texto") String texto, HttpServletRequest request) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            var results = jdbcTemplate.queryForList(queryRepository.getQuery("query14"), texto);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/login/verificar")
public ResponseEntity<Map<String, Object>> verificarLogin(
        @RequestParam String rut,
        @RequestParam String clave,
        HttpServletRequest request) {

    if (accesoDenegado(request)) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    try {
        var resultado = jdbcTemplate.queryForList(
                queryRepository.getQuery("query16"), // Verifica rut + clave
                rut, clave
        );
        if (resultado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(resultado.get(0), HttpStatus.OK);
    } catch (DataAccessException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

@GetMapping("/login/crear")
public ResponseEntity<String> crearUsuarioLogin(
        @RequestParam String rut,
        @RequestParam String clave,
        @RequestParam String categoria,
        HttpServletRequest request) {

    if (accesoDenegado(request)) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    try {
        int filas = jdbcTemplate.update(
                queryRepository.getQuery("query17"), // Inserta en usuarios_login
                rut, clave, categoria
        );
        return filas > 0
                ? ResponseEntity.ok("Usuario creado correctamente")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el usuario");
    } catch (DataAccessException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

@GetMapping("/usuario/datos/crear")
public ResponseEntity<String> crearUsuarioDatos(
        @RequestParam String nombre,
        @RequestParam String rut,
        @RequestParam String direccion,
        @RequestParam String email,
        @RequestParam String telefono,
        @RequestParam String contactoNombre,
        @RequestParam String contactoDireccion,
        @RequestParam String contactoEmail,
        @RequestParam String contactoTelefono,
        HttpServletRequest request) {

    if (accesoDenegado(request)) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    try {
        int filas = jdbcTemplate.update(
                queryRepository.getQuery("query18"), // Inserta en usuarios_datos
                nombre, rut, direccion, email, telefono,
                contactoNombre, contactoDireccion, contactoEmail, contactoTelefono
        );
        return filas > 0
                ? ResponseEntity.ok("Datos personales guardados correctamente")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudieron guardar los datos");
    } catch (DataAccessException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}


    @GetMapping("/alarmas/filtradas")
    public ResponseEntity<List<Map<String, Object>>> filtrarAlarmas(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestParam(required = false) Integer autor,
            HttpServletRequest request
    ) {
        if (accesoDenegado(request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            var results = jdbcTemplate.queryForList(
                    queryRepository.getQuery("query15"),
                    categoria, categoria,
                    texto, texto,
                    fechaInicio, fechaInicio,
                    fechaFin, fechaFin,
                    autor, autor
            );
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
@GetMapping("/login/verificar/admin")
    public ResponseEntity<Map<String, Object>> verificarLoginAdmin(
        @RequestParam String rut,
        @RequestParam String clave,
        HttpServletRequest request) {

    if (accesoDenegado(request)) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    try {
        var resultado = jdbcTemplate.queryForList(
                queryRepository.getQuery("query19"), rut, clave
        );
        if (resultado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(resultado.get(0), HttpStatus.OK);
    } catch (DataAccessException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
}
