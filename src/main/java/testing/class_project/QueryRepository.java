package testing.class_project;

import org.springframework.stereotype.Component;

/**
 * Repositorio centralizado de consultas SQL para las tablas 'usuarios' y
 * 'alarmas'. Organiza y permite reutilizar las consultas en toda la aplicación.
 */
@Component
public class QueryRepository {// Mostrar las últimas 10 alarmas activas (pendientes o en proceso)

    public static final String QUERY_1 = """
    SELECT a.*, u.nombre, u.apellido
    FROM alarmas a
    JOIN usuarios u ON a.usuario_id = u.id
    WHERE a.estado IN ('pendiente', 'en_proceso')
    ORDER BY a.fecha DESC, a.hora DESC
    LIMIT 10;
""";

 public static final String QUERY_2 = """
    SELECT 
        a.id,
        a.descripcion_evento AS descripcion,
        a.fecha,
        a.latitud,
        a.longitud,
        a.estado,
        a.prioridad,
        a.categoria,
        CONCAT(u.nombre, ' ', u.apellido) AS autor
    FROM alarmas a
    LEFT JOIN usuarios u ON a.usuario_id = u.id
    WHERE a.latitud IS NOT NULL AND a.longitud IS NOT NULL
""";


// Ver todas las alarmas de un usuario específico
    public static final String QUERY_3 = """
    SELECT * 
    FROM alarmas
    WHERE usuario_id = ? 
    ORDER BY fecha DESC, hora DESC;
""";

// Consultar alarmas en un rango de fechas
    public static final String QUERY_4 = """
    SELECT * 
    FROM alarmas
    WHERE fecha BETWEEN ? AND ?;
""";

// Contar cuántas alarmas se han generado por cada tipo (categoría)
    public static final String QUERY_5 = """
    SELECT categoria, COUNT(*) AS total
    FROM alarmas
    GROUP BY categoria;
""";

// Contar alarmas según su estado
    public static final String QUERY_6 = """
    SELECT estado, COUNT(*) AS total
    FROM alarmas
    GROUP BY estado;
""";

// Número total de alarmas por usuario
    public static final String QUERY_7 = """
    SELECT u.id, u.nombre, u.apellido, COUNT(a.id) AS total_alarmas
    FROM usuarios u
    LEFT JOIN alarmas a ON u.id = a.usuario_id
    GROUP BY u.id;
""";

// Obtener una alarma específica por ID
    public static final String QUERY_8 = """
    SELECT a.*, u.nombre, u.apellido
    FROM alarmas a
    JOIN usuarios u ON a.usuario_id = u.id
    WHERE a.id = ?;
""";

// Alarmas con prioridad crítica no resueltas
    public static final String QUERY_9 = """
    SELECT a.*, u.nombre, u.apellido
    FROM alarmas a
    JOIN usuarios u ON a.usuario_id = u.id
    WHERE a.prioridad = 'critica' AND a.estado != 'resuelta';
""";

// Alarmas resueltas en los últimos 7 días
    public static final String QUERY_10 = """
    SELECT * 
    FROM alarmas
    WHERE estado = 'resuelta' AND fecha >= CURDATE() - INTERVAL 7 DAY;
""";

// Consultar todas las categorías de alarmas
    public static final String QUERY_11 = """
    SELECT DISTINCT categoria FROM alarmas;
""";

// Consultar todos los usuarios que han generado alarmas
    public static final String QUERY_12 = """
    SELECT DISTINCT nombre_usuario
    FROM alarmas;
""";
// Consultar alarmas por categoría
    // Permite filtrar alarmas por una categoría específica
    public static final String QUERY_13 = """
    SELECT *
    FROM alarmas
    WHERE categoria = ?;
""";
// Consultar alarmas por descripción (búsqueda de texto)
    // Permite buscar alarmas que contengan un texto específico en la descripción
    public static final String QUERY_14 = """
    SELECT * 
    FROM alarmas 
    WHERE LOWER(descripcion) LIKE LOWER(CONCAT('%', ?, '%'));
""";
// Consultar alarmas con múltiples filtros opcionales
    // Permite filtrar por categoría, descripción, rango de fechas y usuario
    public static final String QUERY_15 = """
    SELECT * FROM alarmas
    WHERE (? IS NULL OR categoria = ?)
      AND (? IS NULL OR LOWER(descripcion) LIKE LOWER(CONCAT('%', ?, '%')))
      AND (? IS NULL OR fecha >= ?)
      AND (? IS NULL OR fecha <= ?)
      AND (? IS NULL OR usuario_id = ?);
""";
    
    public static final String QUERY_16 = """
    SELECT *
    FROM usuarios_login
    WHERE rut = ? AND clave = ?;
""";

    public static final String QUERY_17 = """
    INSERT INTO usuarios_login (rut, clave, categoria)
    VALUES (?, ?, ?);
""";

    public static final String QUERY_18 = """
    INSERT INTO usuarios_datos (
        nombre_completo, rut, direccion, email, telefono,
        contacto_emergencia_nombre, contacto_emergencia_direccion,
        contacto_emergencia_email, contacto_emergencia_telefono
    )
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
""";

    // Método para recuperar la consulta deseada por ID
    public String getQuery(String queryId) {
        return switch (queryId) {
            case "query1" ->
                QUERY_1;
            case "query2" ->
                QUERY_2;
            case "query3" ->
                QUERY_3;
            case "query4" ->
                QUERY_4;
            case "query5" ->
                QUERY_5;
            case "query6" ->
                QUERY_6;
            case "query7" ->
                QUERY_7;
            case "query8" ->
                QUERY_8;
            case "query9" ->
                QUERY_9;
            case "query10" ->
                QUERY_10;
            case "query11" ->
                QUERY_11;
            case "query12" ->
                QUERY_12;
            case "query13" ->
                QUERY_13;
            case "query14" ->
                QUERY_14;
            case "query15" ->
                QUERY_15;
            default ->
                throw new IllegalArgumentException("Query no encontrada: " + queryId);
        };
    }
}
