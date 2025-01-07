import  Sequelize  from 'sequelize';

const sequelize = new Sequelize('intercambiosdb', 'root', 'root', {
    host: 'localhost',
    dialect: 'mysql',
    pool: {
        max: 1000,      // Máximo número de conexiones
        min: 0,         // Mínimo número de conexiones
        acquire: 30000, // Tiempo máximo en milisegundos que Sequelize esperará por una conexión
        idle: 10000,    // Tiempo máximo en milisegundos que una conexión puede estar inactiva antes de ser liberada
    },
    logging: false, 
});

export default sequelize;
