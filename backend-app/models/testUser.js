const User = require('./user');

(async () => {
    try {
        console.log('Testing findByUsernameOrEmail...');
        const user = await User.findByUsernameOrEmail('test_user', 'test_user@example.com');
        console.log('Result:', user);

        console.log('Testing create...');
        const userId = await User.create('new_user', 'new_user@example.com', 'hashed_password');
        console.log('Inserted ID:', userId);

        console.log('Testing findByEmail...');
        const userByEmail = await User.findByEmail('new_user@example.com');
        console.log('Result:', userByEmail);
    } catch (error) {
        console.error('Error:', error);
    }
})();
