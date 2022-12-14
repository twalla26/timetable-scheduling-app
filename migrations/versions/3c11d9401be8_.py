"""empty message

Revision ID: 3c11d9401be8
Revises: 7ab8a6c471db
Create Date: 2022-12-07 14:15:54.765485

"""
from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision = '3c11d9401be8'
down_revision = '7ab8a6c471db'
branch_labels = None
depends_on = None


def upgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    with op.batch_alter_table('plan', schema=None) as batch_op:
        batch_op.add_column(sa.Column('code', sa.String(length=150), nullable=False))
        batch_op.drop_constraint('uq_plan_random_string', type_='unique')
        batch_op.create_unique_constraint(batch_op.f('uq_plan_code'), ['code'])
        batch_op.drop_column('random_string')

    # ### end Alembic commands ###


def downgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    with op.batch_alter_table('plan', schema=None) as batch_op:
        batch_op.add_column(sa.Column('random_string', sa.VARCHAR(length=150), nullable=False))
        batch_op.drop_constraint(batch_op.f('uq_plan_code'), type_='unique')
        batch_op.create_unique_constraint('uq_plan_random_string', ['random_string'])
        batch_op.drop_column('code')

    # ### end Alembic commands ###