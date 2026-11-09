import requests
import uuid

BASE_URL = "http://localhost:8080"

def registration():
    username = "testuser"
    password = "testpass"

    register_url = f"{BASE_URL}/register"

    data = {
        "name": username,
        "password": password
    }

    res = requests.post(
        register_url,
        json=data,
        timeout=5
    )

    if res.status_code in [200, 201]:
        return True

    return False

def check():
    passed = []

    print(f"registration {'pass' if registration() else 'fail'}")

check()
