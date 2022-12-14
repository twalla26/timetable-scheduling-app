"""empty message

Revision ID: 61bada784bdd
Revises: 4b7351342229
Create Date: 2022-12-10 21:05:04.897732

"""
from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision = '61bada784bdd'
down_revision = '4b7351342229'
branch_labels = None
depends_on = None


def upgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    with op.batch_alter_table('schedule', schema=None) as batch_op:
        batch_op.add_column(sa.Column('possible_time', sa.String(), nullable=False))
        batch_op.add_column(sa.Column('ambiguous_time', sa.String(), nullable=True))
        batch_op.add_column(sa.Column('impossible_time', sa.String(), nullable=True))
        batch_op.drop_column('possible_day')
        batch_op.drop_column('impossible_day')
        batch_op.drop_column('ambiguous_day')

    # ### end Alembic commands ###


def downgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    with op.batch_alter_table('schedule', schema=None) as batch_op:
        batch_op.add_column(sa.Column('ambiguous_day', sa.VARCHAR(), nullable=True))
        batch_op.add_column(sa.Column('impossible_day', sa.VARCHAR(), nullable=True))
        batch_op.add_column(sa.Column('possible_day', sa.VARCHAR(), nullable=False))
        batch_op.drop_column('impossible_time')
        batch_op.drop_column('ambiguous_time')
        batch_op.drop_column('possible_time')

    # ### end Alembic commands ###
