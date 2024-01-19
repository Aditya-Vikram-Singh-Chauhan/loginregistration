// Your code for sending OTP by SMS here
        const twilio = require('twilio');
        const client = twilio(accountSid, authToken);
        function sendOTPBySMS(phoneNumber, otp) {
        client.messages
            .create({
                body: `Your OTP code to login is: ${otp}`,
                from: '7338637202', // Your Twilio phone number
                to: phoneNumber, // Recipient's phone number
            })
            .then(message => console.log('OTP sent:', message.sid))
            .catch(error => console.error('Error sending OTP:', error));
    }
