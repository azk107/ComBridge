const db = require('../config/database');

const User = {
    findByUsernameOrEmail: async (username, email) => {
        const [rows] = await db.query(
            'SELECT * FROM users WHERE username = ? OR email = ?',
            [username, email]
        );
        return rows[0]; // Kembalikan pengguna pertama yang ditemukan
    },

    create: async (username, email, password) => {
        const [result] = await db.query(
            'INSERT INTO users (username, email, password, createdAt, updatedAt) VALUES (?, ?, ?, NOW(), NOW())',
            [username, email, password]
        );
        return result.insertId;
    },

    findByEmail: async (email) => {
        const [rows] = await db.query('SELECT * FROM users WHERE email = ?', [email]);
        return rows[0];
    },
};

module.exports = User;
