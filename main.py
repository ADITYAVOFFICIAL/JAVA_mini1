from googletrans import Translator
import google.generativeai as palm
import re

translator = Translator()
cho = int(input("Enter 0 for translation or 1 for grammar correction: "))

# Function to remove special characters
def remove_special_chars(text):
    pattern = r'[*#]'
    return re.sub(pattern, '', text)

palm.configure(api_key='YOUR_API_KEY')  

# List available models
models = [m for m in palm.list_models() if 'generateText' in m.supported_generation_methods]
model = models[0].name

# Dictionary to map language names to language codes
language_codes = {
    'Afrikaans': 'af',
    'Albanian': 'sq',
    'Arabic': 'ar',
    'Armenian': 'hy',
    'Azerbaijani': 'az',
    'Bengali': 'bn',
    'Bosnian': 'bs',
    'Bulgarian': 'bg',
    'Chinese (Simplified)': 'zh-cn',
    'Chinese (Traditional)': 'zh-tw',
    'Croatian': 'hr',
    'Czech': 'cs',
    'Danish': 'da',
    'Dutch': 'nl',
    'Estonian': 'et',
    'Finnish': 'fi',
    'German': 'de',
    'Greek': 'el',
    'Haitian Creole': 'ht',
    'Hebrew': 'he',
    'Hindi': 'hi',
    'Hungarian': 'hu',
    'Icelandic': 'is',
    'Indonesian': 'id',
    'Italian': 'it',
    'Japanese': 'ja',
    'Korean': 'ko',
    'Latvian': 'lv',
    'Lithuanian': 'lt',
    'Macedonian': 'mk',
    'Malay': 'ms',
    'Maltese': 'mt',
    'Norwegian': 'no',
    'Persian': 'fa',
    'Polish': 'pl',
    'Portuguese': 'pt',
    'Romanian': 'ro',
    'Russian': 'ru',
    'Serbian': 'sr',
    'Slovak': 'sk',
    'Slovenian': 'sl',
    'Swahili': 'sw',
    'Swedish': 'sv',
    'Tamil': 'ta',
    'Thai': 'th',
    'Turkish': 'tr',
    'Ukrainian': 'uk',
    'Urdu': 'ur',
    'Vietnamese': 'vi',
    'Welsh': 'cy',
    'Xhosa': 'xh',
    'Yoruba': 'yo',
    'Zulu': 'zu',
    'Kannada': 'kn',
    'Telugu': 'te',
    'Punjabi': 'pa',
    'Gujarati': 'gu',
    'Marathi': 'mr',
    'Oriya': 'or',
    'Bengali': 'bn',
    'Assamese': 'as',
    'Kashmiri': 'ks',
    'Sindhi': 'sd',
    'Nepali': 'ne',
    'Pashto': 'ps',
    'Dari': 'fa-af',
    'Tajik': 'tg',
    'Kazakh': 'kk',
    'Tatar': 'tt',
    'Bashkir': 'ba',
    'Kyrgyz': 'ky',
    'Turkmen': 'tk',
    'Uighur': 'ug',
    'Chuvash': 'cv',
    'Buryat': 'bxr',
    'Mongolian': 'mn',
    'Tibetan': 'bo',
    'Korean': 'ko',
}



if cho == 0:
    text_to_translate = input("Enter the text to be translated: ")
    
    # Get user input for the target language
    modified_str = input("Enter the target language (e.g., English, Spanish, French): ")
    target_language_name = modified_str.title()
    
    # Convert target language name to language code
    target_language_code = language_codes.get(target_language_name, 'en')  # Default to English
    
    # Translate the text
    translated_result = translator.translate(text_to_translate, dest=target_language_code)
    
    # Print the translated text
    print(f"Translated Text: {translated_result.text}")
    
    # Detect the language of the text
    # detected_language = translator.detect(text_to_translate)
    
    # Print the detected language
    # print("Detected Language:", detected_language.lang)
    
else:
    input_para = input("Enter text: ")
    prompt = f'Correct the grammar of the text given in the source language. The text is: {input_para}'
    
    completion = palm.generate_text(
        model=model,
        prompt=prompt,
        temperature=0.7,  # Adjust the temperature as needed
        max_output_tokens=2000,
    )
    
    # Print the fixed text without special characters
    print("Fixed Text:")
    print(remove_special_chars(completion.result))
