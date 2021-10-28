from Crypto.PublicKey import RSA
from Crypto.Random import get_random_bytes
from Crypto.Cipher import AES, PKCS1_OAEP
from base64 import b64encode
from jwcrypto.jwk import JWK
import requests

plain_text = input("Enter SPIN: ")

print(f"plaintext: {plain_text}\n")

print("Getting JWK...\n")
r = requests.get('http://localhost:8080/jwk')
if not r:
    print("Failed to get JWK...", r.status_code, r.text)
public_pem = JWK.from_json(r.text).export_to_pem()
public_key = RSA.import_key(public_pem)

print("Encrypting plain text...")
cipher_rsa = PKCS1_OAEP.new(public_key)
cipher_text = b64encode(cipher_rsa.encrypt(plain_text.encode())).decode()
print(f"cipher text: {cipher_text}\n")

print("Sending ciphertext to server for decryption...")
r = requests.post('http://localhost:8080/decrypt', json={"ciphertext": cipher_text})
if not r:
    print("Server failed to decrypt message...", r.status_code, r.text)
print(f"decrypted: {r.text}")



