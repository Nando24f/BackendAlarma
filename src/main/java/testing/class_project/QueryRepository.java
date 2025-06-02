package testing.class_project;

import org.springframework.stereotype.Component;

/**
 * Repositorio centralizado de consultas SQL para las tablas 'usuarios' y 'alarmas'.
 * Organiza y permite reutilizar las consultas en toda la aplicación.
 */
@Component
public class QueryRepository {

    // Vecinos (usuarios con nivel_user_admin = 0)
    public static final String QUERY_1 = """
        SELECT id, nombre, apellido, fecha_nacimiento, direccion, numero_casa, rut, sexo
        FROM usuarios
        WHERE nivel_user_admin = 0;
    """;

    // Usuarios administradores
    public static final String QUERY_2 = """
        SELECT id, nombre, apellido, fecha_nacimiento, direccion, numero_casa, rut, sexo
        FROM usuarios
        WHERE nivel_user_admin = 1;
    """;

    // Alarmas con datos del usuario
    public static final String QUERY_3 = """
        SELECT a.id, u.nombre, u.apellido, a.direccion_usuario, a.fecha, a.hora
        FROM alarmas a
        JOIN usuarios u ON a.usuario_id = u.id;
    """;

    // Porcentaje de hombres con alarma
    public static final String QUERY_4 = """
        SELECT 
            (COUNT(DISTINCT a.usuario_id) * 100.0 / (SELECT COUNT(DISTINCT usuario_id) FROM alarmas)) AS porcentaje_hombres
        FROM alarmas a
        JOIN usuarios u ON a.usuario_id = u.id
        WHERE u.sexo = 'Hombre';
    """;

    // Porcentaje de mujeres con alarma
    public static final String QUERY_5 = """
        SELECT 
            (COUNT(DISTINCT a.usuario_id) * 100.0 / (SELECT COUNT(DISTINCT usuario_id) FROM alarmas)) AS porcentaje_mujeres
        FROM alarmas a
        JOIN usuarios u ON a.usuario_id = u.id
        WHERE u.sexo = 'Mujer';
    """;

    // Total de hombres registrados
    public static final String QUERY_6 = """
        SELECT COUNT(*) AS cantidad_hombres
        FROM usuarios
        WHERE sexo = 'Hombre';
    """;

    // Total de mujeres registradas
    public static final String QUERY_7 = """
        SELECT COUNT(*) AS cantidad_mujeres
        FROM usuarios
        WHERE sexo = 'Mujer';
    """;

    // Cantidad de vecinos en una calle específica (parámetro: calle)
    public static final String QUERY_8 = """
        SELECT COUNT(*) AS cantidad_vecinos
        FROM usuarios
        WHERE direccion LIKE CONCAT('%', ?, '%') AND nivel_user_admin = 0;
    """;

    // Cantidad de mujeres en una calle específica (parámetro: calle)
    public static final String QUERY_9 = """
        SELECT COUNT(*) AS cantidad_mujeres
        FROM usuarios
        WHERE direccion LIKE CONCAT('%', ?, '%') AND sexo = 'Mujer';
    """;

    // Cantidad de hombres en una calle específica (parámetro: calle)
    public static final String QUERY_10 = """
        SELECT COUNT(*) AS cantidad_hombres
        FROM usuarios
        WHERE direccion LIKE CONCAT('%', ?, '%') AND sexo = 'Hombre';
    """;

    // Lista de calles distintas (sin duplicados)
    public static final String QUERY_11 = """
        SELECT DISTINCT TRIM(direccion) AS calle
        FROM usuarios
        ORDER BY calle;
    """;

    // Método para recuperar la consulta deseada por ID
    public String getQuery(String queryId) {
        return switch (queryId) {
            case "query1" -> QUERY_1;
            case "query2" -> QUERY_2;
            case "query3" -> QUERY_3;
            case "query4" -> QUERY_4;
            case "query5" -> QUERY_5;
            case "query6" -> QUERY_6;
            case "query7" -> QUERY_7;
            case "query8" -> QUERY_8;
            case "query9" -> QUERY_9;
            case "query10" -> QUERY_10;
            case "query11" -> QUERY_11;
            case "query12" -> QUERY_11; // Asumiendo que QUERY_12 es igual a QUERY_11
            default -> throw new IllegalArgumentException("Query no encontrada: " + queryId);
        };
    }
}
