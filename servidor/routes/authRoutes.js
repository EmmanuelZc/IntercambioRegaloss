import express from 'express';
import authControllers from '../Controllers/authControllers.js'; 

const router = express.Router();

router.post('/registro', authControllers.registerUser);
router.post('/login', authControllers.loginUser);

export default router;
