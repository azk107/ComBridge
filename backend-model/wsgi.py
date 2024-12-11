from src.api import create_app

app = create_app()

if __name__ == '__main__':
    from src.config import PORT, HOST
    app.run(host=HOST, port=PORT)