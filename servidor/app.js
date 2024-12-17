import express from 'express';
import authRoutes from './routes/authRoutes.js'
import syncDB from './config/syncDB.js';
import morgan from 'morgan';

// Crear una app de express
const app = express();
const PORT = 3000;

// Middleware para parsear cuerpos de las solicitudes
app.use(express.json());
app.use('/api/auth', authRoutes);
app.use(morgan('dev'))
// Sincronizar la base de datos y luego iniciar el servidor
syncDB().then(() => {
    app.listen(PORT, () => {
        console.log(`Servidor corriendo en el puerto:${PORT}`);
    });
});