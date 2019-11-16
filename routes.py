from flask import render_template, request, redirect, url_for, abort, flash
from flask_login import current_user, login_required, login_user, logout_user
from server import app
from run import *
import datetime
import time
import uuid
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)         # Create a socket object
host = socket.gethostname() # Get local machine name
port = 3038         # Reserve a port for your service.
host = "localhost"
s.connect((host, port))
s.send(b"this is my thank yo\n")
print("Connected1")
uniqueURL1 = uuid.uuid4()
uniqueURL2 = uuid.uuid4()
uniqueURL3 = uuid.uuid4()
uniqueURL4 = uuid.uuid4()



@app.route('/game/<uniqueURL>', methods=['GET', 'POST'])
def index(uniqueURL):
    if request.method == 'POST':
        print("posting")
        print(request.form)
        if "roll" in request.form:
            print("hello")
            #sendMessage("roll")
            s.send(b"roll\n")
        elif "stop" in request.form:
            s.send(b"stop\n")
        elif "next" in request.form:
            pass
        elif "change" in request.form:
            sendMessage("change")
    return render_template('index.html')
'''
@app.route('/', methods=['GET', 'POST'])
def home():
    print(uniqueURL1)
    return render_template('home.html', uniqueURL1 = uniqueURL1, uniqueURL2 = uniqueURL2)
'''
@app.route('/', methods=['GET', 'POST'])
def home():
    if request.method == 'POST':
        print("posting")
        print(request.form)
        if "roll" in request.form:
            print("hello")
            # sendMessage("roll")
            s.send(b"roll\n")
        elif "stop" in request.form:
            s.send(b"stop\n")
        elif "next" in request.form:
            pass
        elif "change" in request.form:
            sendMessage("change")
    return render_template('index.html')