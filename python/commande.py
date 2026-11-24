import ctypes

PRIX = 1500
QUANTITE = 2_000_000

# en python les int n'ont pas de limite, pas de debordement
print("total python        :", PRIX * QUANTITE)

# mais des qu'on passe par un type de taille fixe (C, numpy, struct...) ca revient
print("total en int32 C    :", ctypes.c_int32(PRIX * QUANTITE).value)

try:
    import numpy as np
    print("total numpy int32   :", np.int32(PRIX) * np.int32(QUANTITE))
except ImportError:
    print("numpy pas installe")
