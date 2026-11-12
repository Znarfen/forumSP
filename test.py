import requests
import colorama

PORT = 8080
BASE_URL = f"http://localhost:{PORT}"
USERNAME = "testuser"
PASSWORD = "testpass"
DEBUG = False

passed = 0
total = 0

def check():

    header("ENDPOINT", "SUCCES", "STATUS")

    #
    # POST endpoints
    #
    header("POST", "", "")
    
    result("post /register", conect(
        "POST", 
        "register", 
        {
            "name": USERNAME,
            "password": PASSWORD
        },
        None
    ))

    result("post /register", conect(
        "POST", 
        "register", 
        {
            "name": USERNAME,
            "password": PASSWORD
        },
        None
    ), True)
    
    result("post /addSpace", conect(
        "POST",
        "addSpace",
        {
            "name": "cars",
            "description": "Cars go wrom, wrom!",
            "tags": ["car", "wheel", "clutch", "engine"]
        }
    ))

    result("post /addSpace", conect(
        "POST",
        "addSpace",
        {
            "name": "cars",
            "description": "Cars go wrom, wrom!",
            "tags": ["car", "wheel", "clutch", "engine"]
        }
    ), True)

    result("post /cars/addPost", conect(
        "POST",
        "cars/addPost",
        {
            "title": "Saab 96",
            "content": "A cool car!"
        }
    ))

    #
    # GET endpoint
    #
    header("GET", "", "")

    result("get /users", conect (
        "GET",
        "users"
    ))

    result("get /spaces", conect(
        "GET",
        "users"
    ))

    result("get /getSuggestedSpaces", conect(
        "GET",
        "getSuggestedSpaces",
        {
            "description": "The Saab 96 freewheel is an overrunning clutch built into the transmission that lets the car coast freely when you lift your foot off the gas pedal."
        }
    ))

    result("get /getSuggestedSpaces", conect(
        "GET",
        "getSuggestedSpaces",
        {
            "description": "My pc have a lot of bugs. Eww"
        }
    ), True)


    header("PASSED TESTS:", f"{passed}/{total}", "Test status codes")

def debug(msg):
    if DEBUG: print(msg)
    return

def rowPrint(*col):
    msg = ""
    for s in col:
        msg += form(s)
    print(msg)
    return

def header(*col):
    msg = ""
    for s in col: msg += form("", " ", "|")
    print(msg)
    msg = ""
    for s in col: msg += form("", "-", "+")
    rowPrint(*col)
    print(msg)
    return

def result(msg, res = requests, expectedFail = False):
    success = False
    
    min = 200
    max = 300

    if (expectedFail):
        min = 400
        max = 500

    if (min <= res.status_code < max): success = True
    rowPrint(
          f"{msg}",
          "PASS" if success else "FAIL",
          f"{res.status_code} {"(Expected 400-499)" if expectedFail else " "}"
          )

    return

def form(msg="", spaceChar=" ", seperator="|", seperate=30, spaces=34):
    msg = f"{msg}"

    for i in range(0, spaces):
        if (i != seperate): 
            if (i > len(msg)): msg += spaceChar
        else:
            msg += seperator

    return msg

def conect(type="", endPoint="", data={}, authentication=(USERNAME, PASSWORD), time=5):
    url = f"{BASE_URL}/{endPoint}"

    if (type == "POST"):
        res = requests.post(
            url,
            json=data,
            auth=authentication,
            timeout=time
        )

    else: 
        res = requests.get(
            url,
            auth=authentication,
            timeout=time,
            json=data
        )

    return res


try: check()
except requests.exceptions.ConnectionError: header("NOT CONECTED TO BACKEND", f"PORT: {PORT}")
