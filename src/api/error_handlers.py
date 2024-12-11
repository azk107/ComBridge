"""Error handlers for the API."""
from flask import jsonify
from werkzeug.exceptions import HTTPException

def register_error_handlers(app):
    """Register error handlers for the application."""
    
    @app.errorhandler(HTTPException)
    def handle_http_error(error):
        """Handle HTTP exceptions."""
        response = {
            'error': error.description,
            'status_code': error.code
        }
        return jsonify(response), error.code

    @app.errorhandler(Exception)
    def handle_generic_error(error):
        """Handle generic exceptions."""
        response = {
            'error': str(error),
            'status_code': 500
        }
        return jsonify(response), 500