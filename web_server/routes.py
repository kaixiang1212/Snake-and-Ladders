from flask import render_template, request, redirect, url_for, abort, flash
from flask_login import current_user, login_required, login_user, logout_user
from server import *

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # Create a socket object


@app.route('/game', methods=['GET', 'POST'])
@login_required
def game():
    if request.method == 'POST':
        player = current_user
        print(current_user)
        if "roll" in request.form:
            player.roll()
        elif "stop" in request.form:
            player.stop()
        elif "next" in request.form:
            pass
        elif "change" in request.form:
            pass
    return render_template('index.html')


@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        user = add_player(request.form['player_name'])
        login_user(user)
        return redirect(url_for('game'))
    return render_template('login.html')


@app.route('/')
def index():
    if current_user not in players:
        return redirect(url_for('login'))
    return redirect(url_for('game'))
