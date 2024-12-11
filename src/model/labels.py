"""BISINDO sign language labels."""

LABELS = [
    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
    "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
    "U", "V", "W", "X", "Y", "Z"
]

def get_label_from_index(index: int) -> str:
    """Get label string from prediction index."""
    if 0 <= index < len(LABELS):
        return LABELS[index]
    return "Unknown"