package testing.class_project;

import org.springframework.stereotype.Component;

/**
 * Centralized repository for database queries targeting the 'usuarios' and
 * 'alarmas' database. Keeps SQL organized and reusable across the application.
 */
@Component
public class QueryRepository {

    // Queries como constantes públicas
    public static final String QUERY_1 = """
            SELECT id, nombre, apellido, fecha_nacimiento, direccion, numero_casa, rut, sexo
            FROM usuarios
            WHERE nivel_user_admin = 0;
            """;

    public static final String QUERY_2 = """
            SELECT id, nombre, apellido, fecha_nacimiento, direccion, numero_casa, rut, sexo
            FROM usuarios
            WHERE nivel_user_admin = 1;
            """;

    public static final String QUERY_3 = """
            SELECT a.id, u.nombre, u.apellido, a.direccion_usuario, a.fecha, a.hora
            FROM alarmas a
            JOIN usuarios u ON a.usuario_id = u.id;
            """;

    public static final String QUERY_4 = """
            SELECT 
                (COUNT(DISTINCT a.usuario_id) / (SELECT COUNT(DISTINCT usuario_id) FROM alarmas) * 100) AS porcentaje_hombres
            FROM alarmas a
            JOIN usuarios u ON a.usuario_id = u.id
            WHERE u.sexo = 'Hombre';
            """;

    public static final String QUERY_5 = """
            SELECT 
                (COUNT(DISTINCT a.usuario_id) / (SELECT COUNT(DISTINCT usuario_id) FROM alarmas) * 100) AS porcentaje_mujeres
            FROM alarmas a
            JOIN usuarios u ON a.usuario_id = u.id
            WHERE u.sexo = 'Mujer';
            """;

    public static final String QUERY_6 = """
            SELECT COUNT(*) AS cantidad_hombres
            FROM usuarios
            WHERE sexo = 'Hombre';
            """;

    public static final String QUERY_7 = """
            SELECT COUNT(*) AS cantidad_mujeres
            FROM usuarios
            WHERE sexo = 'Mujer';
            """;

    public static final String QUERY_8 = """
            SELECT COUNT(*) AS cantidad_vecinos
            FROM usuarios
            WHERE direccion LIKE CONCAT('%', ?, '%') AND nivel_user_admin = 0;
            """;

    public static final String QUERY_9 = """
            SELECT COUNT(*) AS cantidad_mujeres
            FROM usuarios
            WHERE direccion LIKE CONCAT('%', ?, '%') AND sexo = 'Mujer';
            """;

    public static final String QUERY_10 = """
            SELECT COUNT(*) AS cantidad_hombres
            FROM usuarios
            WHERE direccion LIKE CONCAT('%', ?, '%') AND sexo = 'Hombre';
            """;

    public static final String QUERY_11 = """
        SELECT DISTINCT 
            CASE 
                WHEN direccion REGEXP '[0-9]' THEN 
                    TRIM(SUBSTRING_INDEX(direccion, CONCAT(
                        REGEXP_SUBSTR(direccion, '[0-9]+'), 1))
                ELSE 
                    TRIM(direccion)
            END AS calle
        FROM usuarios
        ORDER BY calle;
        """;

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
            default ->
                throw new IllegalArgumentException("Query not found: " + queryId);
        };
    }
}
