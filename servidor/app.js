import express from 'express';
import authRoutes from './routes/authRoutes.js';
import syncDB from './config/syncDB.js';
import morgan from 'morgan';
import dotenv from 'dotenv';
import setupAssociations from './models/associations.js';

dotenv.config({ path: './llave.env' }); // Cargar variables de entorno

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(express.json());
app.use(morgan('dev'));

// Configurar asociaciones y rutas
setupAssociations();
app.use('/api/auth', authRoutes);

// Manejadores de errores
app.use((req, res, next) => {
    res.status(404).json({ message: 'Ruta no encontrada' });
});

app.use((err, req, res, next) => {
    console.error(err.stack);
    res.status(500).json({ message: 'Error interno del servidor' });
});

// Sincronizar base de datos y levantar servidor
syncDB().then(() => {
    app.listen(PORT, () => {
        console.log(`Servidor corriendo en el puerto: ${PORT}`);
    });
});
