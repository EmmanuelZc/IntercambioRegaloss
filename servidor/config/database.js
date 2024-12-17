import  Sequelize  from 'sequelize';

// Crear una instancia de Sequelize con la configuración de la base de datos
const sequelize = new Sequelize('intercambiosdb', 'root', 'root', {
    host: 'localhost',
    dialect: 'mysql',
    pool: {
        max: 1000,      // Máximo número de conexiones
        min: 0,         // Mínimo número de conexiones
        acquire: 30000, // Tiempo máximo en milisegundos que Sequelize esperará por una conexión
        idle: 10000,    // Tiempo máximo en milisegundos que una conexión puede estar inactiva antes de ser liberada
    },
    logging: false, // Desactiva el logging de consultas SQL
});

export default sequelize;
