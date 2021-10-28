# SPIN Transmission Demo

This repo contains a server that
* serves an RSA public key as JWK at `GET /jwk`
* offers an endpoint at `POST /decrypt` which will decrypt incoming ciphertext and return it

Start the server by running `./gradlew bootRun`.

## Clients

This repo also contains client implementations that ought to demonstrate the integration.

### JS (Browser)
Navigate to [http://localhost:8080/index.html](http://localhost:8080/index.html), insert a message, and hit the button.

### Python
Start `client.py` and insert a message to encrypt/decrypt.
```
# activate venv
pip install -r requirements.txt
python client.py
```
