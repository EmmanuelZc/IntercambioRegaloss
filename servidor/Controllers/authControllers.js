import User from '../models/users.js'; // Modelo de usuario
import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';
import Intercambio from '../models/Intercambio.js'; // Modelo de Intercambio
import Participante from '../models/participante.js'; // Modelo de Participante
import { v4 as uuidv4 } from 'uuid'; // Generar claves únicas (UUID)

// Registro de usuario
const registerUser = async (req, res) => {
    const { nombre, alias, correo, contraseña } = req.body;

    // Validación de datos
    if (!nombre || !alias || !correo || !contraseña) {
        return res.status(400).json({ message: 'Faltan datos' });
    }

    try {
        // Verificar si el correo ya está registrado
        const existingUser = await User.findOne({ correo });
        if (existingUser) {
            return res.status(400).json({ message: 'El correo ya está registrado' });
        }

        // Encriptar contraseña
        const hashedPassword = await bcrypt.hash(contraseña, 10);

        // Crear usuario
        const user = await User.create({
            nombre,
            alias,
            correo,
            contraseña: hashedPassword,
        });

        res.status(201).json({ message: 'Usuario registrado con éxito', user });
    } catch (err) {
        console.error('Error al registrar usuario:', err);
        res.status(500).json({ message: 'Error al registrar el usuario' });
    }
};

const loginUser = async (req, res) => {
    const { correo, contraseña } = req.body;

    if (!correo || !contraseña) {
        console.log('Datos incompletos en login:', req.body); // Log para debugging
        return res.status(400).json({ message: 'Faltan datos' });
    }

    try {
        const user = await User.findOne({ where: { correo } });
        if (!user) {
            console.log('Usuario no encontrado:', correo); // Log
            return res.status(404).json({ message: 'Usuario no encontrado' });
        }

        const isPasswordValid = await bcrypt.compare(contraseña, user.contraseña);
        if (!isPasswordValid) {
            console.log('Contraseña incorrecta para el usuario:', correo); // Log
            return res.status(401).json({ message: 'Contraseña incorrecta' });
        }

        const token = jwt.sign(
            { userId: user.id },
            process.env.JWT_SECRET,
            { expiresIn: '1h' }
        );

        console.log('Inicio de sesión exitoso. Usuario ID:', user.id); // Log
        res.json({
            message: 'Inicio de sesión exitoso',
            token,
            user: {
                id: user.id,
                nombre: user.nombre,
                alias: user.alias,
                correo: user.correo,
            },
        });
    } catch (err) {
        console.error('Error al iniciar sesión:', err); // Log
        res.status(500).json({ message: 'Error al iniciar sesión' });
    }
};

const authenticate = (req, res, next) => {
    const authHeader = req.headers.authorization;

    if (!authHeader) {
        console.log('Falta el encabezado Authorization');
        return res.status(401).json({ message: 'No autorizado: falta token' });
    }

    const token = authHeader.split(' ')[1];
    try {
        const decoded = jwt.verify(token, process.env.JWT_SECRET);
        req.userId = decoded.userId; // Extraer el userId del token
        next(); // Pasar al siguiente middleware o controlador
    } catch (err) {
        console.error('Token inválido:', err);
        res.status(403).json({ message: 'Token inválido' });
    }
};



const createIntercambio = async (req, res) => {
    const {
        nombre,
        fechaLimiteRegistro,
        fechaIntercambio,
        horaIntercambio,
        lugar,
        montoMaximo,
        comentarios,
    } = req.body;

    console.log('Usuario autenticado:', req.userId);
    console.log('Datos del intercambio:', req.body);

    if (!nombre || !fechaLimiteRegistro || !fechaIntercambio || !horaIntercambio || !lugar || montoMaximo === undefined) {
        console.log('Datos incompletos:', req.body); // Log
        return res.status(400).json({ message: 'Faltan datos obligatorios' });
    }

    if (!req.userId) {
        console.log('Usuario no autenticado'); // Log de autenticación fallida
        return res.status(401).json({ message: 'Usuario no autenticado' });
    }

    try {
        const claveUnica = uuidv4();

        console.log('Datos para crear el intercambio:', {
            nombre_intercambio: nombre,
            clave_unica: claveUnica,
            id_user: req.userId,
            fecha_limite_registro: fechaLimiteRegistro,
            fecha_intercambio: fechaIntercambio,
            hora_intercambio: horaIntercambio,
            lugar_intercambio: lugar,
            monto: montoMaximo,
            comentarios,
        });

        const intercambio = await Intercambio.create({
            nombre_intercambio: nombre,
            clave_unica: claveUnica,
            id_user: req.userId,
            fecha_limite_registro: fechaLimiteRegistro,
            fecha_intercambio: fechaIntercambio,
            hora_intercambio: horaIntercambio,
            lugar_intercambio: lugar,
            monto: montoMaximo,
            comentarios,
        });
        

        console.log('Intercambio creado con éxito:', intercambio); // Log del éxito
        res.status(201).json({
            message: 'Intercambio creado con éxito',
            intercambio: {
                id: intercambio.id,
                nombre: intercambio.nombre_intercambio,
                claveUnica: intercambio.clave_unica,
            },
        });
    } catch (error) {
        console.error('Error al crear el intercambio:', error); // Log del error
        res.status(500).json({ message: 'Error interno del servidor' });
    }
};






export default { registerUser, loginUser, createIntercambio,authenticate };
