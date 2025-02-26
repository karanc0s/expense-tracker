from flask import Flask, jsonify
from app.services.Scraper import Scraper
import os


app = Flask(__name__)
processingService = Scraper()


@app.route('/')
def hello_world():
    return 'Hello, Wordsdfs dfs fs f sdf sd ld!'


@app.route('/p')
def processMessage():
    result = processingService.process(
        "Bank Alert: ₹250.00 spent at Starbucks on 23-Feb-2025 at 10:30 AM. If this wasn't you, report immediately."
    )
    print(result)
    return result.model_dump_json()




if __name__ == '__main__':
    PORT = int(os.getenv("PORT", 8000))
    app.run( port = PORT , debug=True)