import os
import matplotlib.pyplot as plt
from matplotlib.lines import Line2D
import pandas as pd

# ---------------------------------------------------------------------------
# Paths
# ---------------------------------------------------------------------------
script_dir = os.path.dirname(os.path.abspath(__file__))
v1_csv = os.path.join(script_dir, "CustomStackV1_wide_matrix.csv")
v2_csv = os.path.join(script_dir, "CustomStackV2_wide_matrix.csv")
jdk_csv = os.path.join(script_dir, "Stack_wide_matrix.csv")

# Verify files exist before loading
for path in [v1_csv, v2_csv, jdk_csv]:
    if not os.path.exists(path):
        raise FileNotFoundError(f"Required CSV file not found: {path}. Please place it in {script_dir}")

# ---------------------------------------------------------------------------
# Load CSV results
# ---------------------------------------------------------------------------
v1_df = pd.read_csv(v1_csv, sep=';', engine='python')
v2_df = pd.read_csv(v2_csv, sep=';', engine='python')
jdk_df = pd.read_csv(jdk_csv, sep=';', engine='python')

# Clean column names (remove quotes if present)
v1_df.columns = [col.strip().replace('"', '') for col in v1_df.columns]
v2_df.columns = [col.strip().replace('"', '') for col in v2_df.columns]
jdk_df.columns = [col.strip().replace('"', '') for col in jdk_df.columns]

# Ensure Size is present and sort by Size
for df in [v1_df, v2_df, jdk_df]:
    if "Size" in df.columns:
        df.sort_values("Size", inplace=True)

# Common columns to plot (excluding Size)
valid_cols = [
    col for col in v2_df.columns
    if col in v1_df.columns and col in jdk_df.columns and col != "Size"
]

# ---------------------------------------------------------------------------
# Plotting
# ---------------------------------------------------------------------------
color_v1 = "#4da6ff"    # Blue for V1
color_v2 = "#ff4d4d"    # Red for V2
color_jdk = "#50c878"   # Green for JDK Stack

for method in valid_cols:
    fig, ax = plt.subplots(figsize=(8, 5.5))

    ax.plot(
        v1_df["Size"],
        v1_df[method],
        color=color_v1,
        marker="o",
        markersize=5,
        linestyle="-",
        linewidth=2,
    )
    ax.plot(
        v2_df["Size"],
        v2_df[method],
        color=color_v2,
        marker="o",
        markersize=5,
        linestyle="-",
        linewidth=2,
    )
    ax.plot(
        jdk_df["Size"],
        jdk_df[method],
        color=color_jdk,
        marker="o",
        markersize=5,
        linestyle="-",
        linewidth=2,
    )

    ax.set_xlim(left=v2_df["Size"].min(), right=v2_df["Size"].max())

    title = method

    # Dark-mode friendly styling
    ax.set_title(title, fontsize=14, fontweight="bold", color="white", pad=15)
    ax.set_xlabel("Size", fontsize=11, color="white")
    ax.set_ylabel("Time (ns/op)", fontsize=11, color="white")
    ax.tick_params(axis="both", colors="white")
    ax.grid(True, linestyle="--", alpha=0.3, color="white")
    for spine in ax.spines.values():
        spine.set_color("white")

    legend_elements = [
        Line2D([0], [0], marker="o", color="none", label="V1", markerfacecolor=color_v1, markeredgecolor=color_v1, markersize=8, linestyle="None"),
        Line2D([0], [0], marker="o", color="none", label="V2", markerfacecolor=color_v2, markeredgecolor=color_v2, markersize=8, linestyle="None"),
        Line2D([0], [0], marker="o", color="none", label="JDK", markerfacecolor=color_jdk, markeredgecolor=color_jdk, markersize=8, linestyle="None"),
    ]
    legend = ax.legend(
        handles=legend_elements,
        loc="upper center",
        bbox_to_anchor=(0.5, -0.15),
        fontsize=10,
        frameon=False,
        ncol=3,
    )
    for text in legend.get_texts():
        text.set_color("white")

    fig.patch.set_alpha(0.0)
    ax.patch.set_alpha(0.0)

    plt.tight_layout()

    safe_filename = (
        method.replace("(", "_")
        .replace(")", "_")
        .replace(",", "_")
        .replace(".", "_")
        .replace(" ", "_")
        .replace("[", "_")
        .replace("]", "_")
    )
    output_image_path = os.path.join(script_dir, f"plot_{safe_filename}.png")
    plt.savefig(output_image_path, transparent=True, bbox_inches="tight")
    plt.close()

print(f"Successfully generated {len(valid_cols)} performance graphs comparing V1, V2, and JDK Stack in {script_dir}")