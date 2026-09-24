import os
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# ---------------------------------------------------------------------------
# Paths
# ---------------------------------------------------------------------------
script_dir = os.path.dirname(os.path.abspath(__file__))
v2_csv = os.path.join(script_dir, 'CustomStackV2_wide_matrix.csv')
v1_csv = os.path.join(script_dir, 'CustomStackV1_wide_matrix.csv')

# Load data files
# CustomStack V2 acts as the primary comparison, V1 as the baseline
v2_df = pd.read_csv(v2_csv, sep=';')
v1_df = pd.read_csv(v1_csv, sep=';')

# Clean headers
v2_df.columns = [c.replace('"', '').strip() for c in v2_df.columns]
v1_df.columns = [c.replace('"', '').strip() for c in v1_df.columns]

# Find common sizes and methods present in both versions
common_sizes = sorted(list(set(v2_df['Size']).intersection(set(v1_df['Size']))))
v2_methods = [c for c in v2_df.columns if c != 'Size']
v1_methods = [c for c in v1_df.columns if c != 'Size']
common_methods = [m for m in v2_methods if m in v1_methods]

heatmap_data = np.zeros((len(common_methods), len(common_sizes)))
text_labels = []

for i, m in enumerate(common_methods):
    row_labels = []
    for j, size in enumerate(common_sizes):
        v2_val = v2_df.loc[v2_df['Size'] == size, m].values[0]
        v1_val = v1_df.loc[v1_df['Size'] == size, m].values[0]

        if v2_val == 0: v2_val = 1
        if v1_val == 0: v1_val = 1

        # log2 ratio: positive means V2 is faster (V1 took more time / higher ns/op)
        ratio = np.log2(v1_val / v2_val)
        heatmap_data[i, j] = ratio

        if v1_val >= v2_val:
            factor = v1_val / v2_val
            row_labels.append(f"+{factor:.1f}x" if factor < 100 else f"+{factor:.0f}x")
        else:
            factor = v2_val / v1_val
            row_labels.append(f"-{factor:.1f}x" if factor < 100 else f"-{factor:.0f}x")
    text_labels.append(row_labels)

text_labels = np.array(text_labels)

# Sort methods by average performance ratio
avg_ratios = np.mean(heatmap_data, axis=1)
sorted_idx = np.argsort(avg_ratios)

heatmap_data = heatmap_data[sorted_idx]
text_labels = text_labels[sorted_idx]
sorted_methods = [common_methods[idx] for idx in sorted_idx]

# Plotting the heatmap - dynamically sized based on method count
fig, ax = plt.subplots(figsize=(14, max(8, len(sorted_methods) * 0.45)), facecolor='none')
ax.set_facecolor('none')

clipped_data = np.clip(heatmap_data, -4.0, 4.0)
cmap = sns.diverging_palette(15, 240, as_cmap=True)

sns.heatmap(clipped_data,
            annot=text_labels,
            fmt="",
            cmap=cmap,
            center=0,
            xticklabels=common_sizes,
            yticklabels=sorted_methods,
            ax=ax,
            cbar_kws={
                'label': '← V1 Faster  |  Relative Speedup Scale (Clipped at 16x)  |  V2 Faster →'},
            linewidths=0.5,
            linecolor='#444444',
            annot_kws={'size': 8, 'weight': 'bold'})

ax.set_title(
    'CustomStack V2 vs V1 Performance Comparison Matrix Heatmap\n(Positive/Blue = V2 Faster, Negative/Red = V1 Faster)',
    color='#ffffff', fontsize=16, fontweight='bold', pad=25)
ax.set_ylabel('Stack Interface Methods', color='#aaaaaa', fontsize=13, labelpad=12)
ax.set_xlabel('Collection Size (Elements)', color='#aaaaaa', fontsize=13, labelpad=12)

ax.tick_params(colors='#ffffff', labelsize=10)
plt.xticks(rotation=45)
plt.yticks(rotation=0)

cbar = ax.collections[0].colorbar
cbar.ax.tick_params(colors='#ffffff', labelsize=10)
cbar.ax.yaxis.label.set_color('#ffffff')
cbar.ax.yaxis.label.set_fontsize(11)

plt.tight_layout()
output_filename = os.path.join(script_dir, 'heatmap.png')
plt.savefig(output_filename, dpi=300, transparent=True)
plt.close()

print(f"Heatmap saved successfully as {output_filename}")
print("Top 5 relatively slower methods for V2 compared to V1 on average:")
print(sorted_methods[:5])
print("Top 5 relatively faster methods for V2 compared to V1 on average:")
print(sorted_methods[-5:])