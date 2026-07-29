import json
import re
from collections import Counter
from pathlib import Path

FILES = [
    "src/main/resources/translations/es/menu/options.json",
    "src/main/resources/translations/es/menu/targets.json",
    "src/main/resources/translations/es/objects/objects.json",
    "src/main/resources/translations/es/npcs/npcs.json",
    "src/main/resources/translations/es/items/items.json",
    "src/main/resources/translations/es/widgets/widgets.json",
]

has_error = False

for file_path in FILES:
    path = Path(file_path)

    if not path.exists():
        print(f"❌ No existe: {file_path}")
        has_error = True
        continue

    text = path.read_text(encoding="utf-8")

    keys = []
    for line_number, line in enumerate(text.splitlines(), start=1):
        match = re.match(r'\s*"([^"]+)"\s*:', line)
        if match:
            keys.append((match.group(1), line_number))

    counter = Counter(key for key, _ in keys)

    duplicates = {
        key: [line for found_key, line in keys if found_key == key]
        for key, count in counter.items()
        if count > 1
    }

    if duplicates:
        print(f"❌ Duplicados en {file_path}:")
        for key, lines in duplicates.items():
            print(f"   - {key}: líneas {lines}")
        has_error = True

    try:
        json.loads(text)
    except Exception as error:
        print(f"❌ JSON inválido en {file_path}: {error}")
        has_error = True
        continue

    if not duplicates:
        print(f"✅ OK: {file_path}")

if has_error:
    raise SystemExit(1)

print("✅ Todas las traducciones están OK.")